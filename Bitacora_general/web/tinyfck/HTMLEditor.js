tinyMCE.init({
                mode : "exact",
                elements: "descripcion-id",
                plugins : "style,advimage,advlink,emotions,insertdatetime,preview,zoom,flash,searchreplace,print,paste,directionality,fullscreen",
                theme_advanced_buttons1_add_before : "save,newdocument,separator",
                theme_advanced_buttons1_add : "fontselect,fontsizeselect",
                theme_advanced_buttons2_add : "separator,",
                theme_advanced_buttons2_add_before: "cut,copy,paste,pastetext,pasteword",
                theme_advanced_buttons3_add : "emotions,iespell,flash,separator,print,separator,separator,fullscreen,insertdate,inserttime,preview,separator,forecolor,backcolor",
                theme_advanced_toolbar_location : "top",
                theme_advanced_toolbar_align : "left",
                theme_advanced_path_location : "bottom",
                plugin_insertdate_dateFormat : "%Y-%m-%d",
                plugin_insertdate_timeFormat : "%H:%M:%S",
                file_browser_callback : "fileBrowserCallBack",
                paste_use_dialog : false,
		theme_advanced_resizing : true,
		theme_advanced_resize_horizontal : false,
		theme_advanced_link_targets : "_something=My somthing;_something2=My somthing2;_something3=My somthing3;",
		apply_source_formatting : true,
                contextmenu : false
//                language : "zh_cn_utf8"
            });

            function fileBrowserCallBack(field_name, url, type, win) {
                var connector = "../../filemanager/browser.html?Connector=connectors/jsp/connector";
                var enableAutoTypeSelection = true;

                var cType;
                tinyfck_field = field_name;
                tinyfck = win;

                switch (type) {
                    case "image":
                        cType = "Image";
                        break;
                    case "flash":
                        cType = "Flash";
                        break;
                    case "file":
                        cType = "File";
                        break;
                }

                if (enableAutoTypeSelection && cType) {
                    connector += "&Type=" + cType;
                }
                window.open(connector, "tinyfck", "modal,width=500,height=400");
            }

