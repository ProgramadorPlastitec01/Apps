
package Servlet;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;

@MultipartConfig
public class ConnectorServlet extends HttpServlet {

    private static final String ROOT_DIR = "/WebContent/Uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        if ("open".equals(cmd)) {
            handleOpen(request, response);
        } else {
            sendError(response, "Comando no soportado por GET: " + cmd);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        switch (cmd) {
            case "open": handleOpen(request, response); break;
            case "upload": handleUpload(request, response); break;
            case "mkdir": handleMkdir(request, response); break;
            case "mkfile": handleMkfile(request, response); break;
            case "rm": handleRemove(request, response); break;
            default: sendError(response, "Comando POST no soportado: " + cmd); break;
        }
    }

    private void handleOpen(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File dir = new File(getServletContext().getRealPath(ROOT_DIR));
        if (!dir.exists()) dir.mkdirs();

        JSONObject json = new JSONObject();
        JSONArray files = new JSONArray();
        JSONObject cwd = new JSONObject();

        cwd.put("name", "Uploads");
        cwd.put("hash", "l1_Lw");
        cwd.put("mime", "directory");
        cwd.put("ts", System.currentTimeMillis() / 1000);
        cwd.put("read", 1);
        cwd.put("write", 1);
        cwd.put("size", 0);
        cwd.put("dirs", 1);
        json.put("cwd", cwd);

        for (File file : dir.listFiles()) {
            JSONObject f = new JSONObject();
            f.put("name", file.getName());
            f.put("hash", "l1_" + URLEncoder.encode(file.getName(), "UTF-8"));
            f.put("phash", "l1_Lw");
            f.put("mime", file.isDirectory() ? "directory" : getMimeType(file));
            f.put("ts", file.lastModified() / 1000);
            f.put("size", file.length());
            f.put("read", 1);
            f.put("write", 1);
            f.put("locked", 0);
            f.put("dirs", file.isDirectory() ? 1 : 0);
            files.put(f);
        }

        json.put("files", files);
        json.put("options", new JSONObject().put("path", "Uploads").put("url", request.getContextPath() + ROOT_DIR + "/"));
        json.put("uplMaxSize", "100M");

        sendJson(response, json);
    }

    private void handleMkdir(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        File newDir = new File(getServletContext().getRealPath(ROOT_DIR + "/" + name));
        JSONObject json = new JSONObject();

        if (!newDir.exists()) {
            if (newDir.mkdir()) {
                JSONObject f = new JSONObject();
                f.put("name", name);
                f.put("hash", "l1_" + URLEncoder.encode(name, "UTF-8"));
                f.put("phash", "l1_Lw");
                f.put("mime", "directory");
                f.put("ts", System.currentTimeMillis() / 1000);
                f.put("size", 0);
                f.put("read", 1);
                f.put("write", 1);
                f.put("locked", 0);
                f.put("dirs", 1);
                json.put("added", new JSONArray().put(f));
                sendJson(response, json);
                return;
            }
        }
        sendError(response, "No se pudo crear la carpeta.");
    }

    private void handleMkfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        File newFile = new File(getServletContext().getRealPath(ROOT_DIR + "/" + name));

        if (!newFile.exists()) {
            if (newFile.createNewFile()) {
                JSONObject json = new JSONObject();
                JSONObject f = new JSONObject();
                f.put("name", name);
                f.put("hash", "l1_" + URLEncoder.encode(name, "UTF-8"));
                f.put("phash", "l1_Lw");
                f.put("mime", "application/octet-stream");
                f.put("ts", System.currentTimeMillis() / 1000);
                f.put("size", 0);
                f.put("read", 1);
                f.put("write", 1);
                f.put("locked", 0);
                f.put("dirs", 0);
                json.put("added", new JSONArray().put(f));
                sendJson(response, json);
                return;
            }
        }
        sendError(response, "No se pudo crear el archivo.");
    }

    private void handleUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = request.getPart("upload[]");
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            String type = filePart.getContentType().split("/")[0];
            File dir = new File(getServletContext().getRealPath(ROOT_DIR + "/" + type + "s"));
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, fileName);
            filePart.write(file.getAbsolutePath());

            JSONObject json = new JSONObject();
            JSONArray added = new JSONArray();
            JSONObject f = new JSONObject();
            f.put("name", fileName);
            f.put("hash", "l1_" + URLEncoder.encode(fileName, "UTF-8"));
            f.put("phash", "l1_Lw");
            f.put("mime", getMimeType(file));
            f.put("ts", file.lastModified() / 1000);
            f.put("size", file.length());
            f.put("read", 1);
            f.put("write", 1);
            f.put("locked", 0);
            f.put("dirs", 0);
            added.put(f);
            json.put("added", added);
            sendJson(response, json);
        } else {
            sendError(response, "Archivo no recibido.");
        }
    }

    private void handleRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] targets = request.getParameterValues("targets[]");
        JSONArray removed = new JSONArray();
        if (targets != null) {
            for (String hash : targets) {
                String name = java.net.URLDecoder.decode(hash.replace("l1_", ""), "UTF-8");
                File file = new File(getServletContext().getRealPath(ROOT_DIR + "/" + name));
                if (file.exists()) {
                    deleteRecursive(file);
                    removed.put(hash);
                }
            }
        }
        JSONObject json = new JSONObject();
        json.put("removed", removed);
        sendJson(response, json);
    }

    private boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) deleteRecursive(child);
        }
        return file.delete();
    }

    private String getMimeType(File file) {
        String mime = URLConnection.guessContentTypeFromName(file.getName());
        return (mime != null) ? mime : "application/octet-stream";
    }

    private void sendJson(HttpServletResponse response, JSONObject json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
    }

    private void sendError(HttpServletResponse response, String msg) throws IOException {
        JSONObject json = new JSONObject();
        json.put("error", new JSONArray().put(msg));
        sendJson(response, json);
    }
}
