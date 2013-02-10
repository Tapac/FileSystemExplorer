(function ($) {
    $.widget('ui.fsexplorer', {
        options: {
            serviceUrl: "http://" + window.location.host + "/service/",
            render: "server",
            template: ""
        },
        loadNodes: function (node) {
            var o = this.options;
            var dataService = "children/" + window.btoa(window.atob($(node).attr('fullpath')) + $(node).attr('data-handler'));
            var toAppend = (node.attr('fullpath') == "") ? node : node.after('<li class="subnodes"></li>').next();

            var url = "";
            if (o.render == "server") {
                url = encodeURI(this.options.serviceUrl + "rendered/" + dataService);
                toAppend.load(url, handleError);
            } else {
                url = encodeURI(this.options.serviceUrl + dataService);
                $.getJSON(url,function (json) {
                    toAppend.html(o.template(json));
                }).fail(function (jqXHR, status, exception) {
                        handleError("", status, jqXHR);
                    });
            }

            function handleError(response, status, xhr) {
                if (status == "error") {
                    toAppend.remove();
                    console.warn(xhr.responseText);
                    alert(xhr.responseText);
                }
            }

        },


        _create: function () {
            var self = this,
                el = self.element,
                o = self.options;
            $(el).addClass("fsexplorer");
            $(el).on("click", "li.folder, li.arc", function () {
                self._handleClick($(this))
            });
            if (typeof Mustache == 'undefined') {
                o.render = "server";
            }

            if (o.render == "client") {
                $.get(o.serviceUrl + "template")
                    .done(function (data) {
                        o.template = Mustache.compile(data);
                        self.loadNodes($(el).attr('fullpath', '').attr('data-handler', ''));
                    });
            } else {
                this.loadNodes($(el).attr('fullpath', '').attr('data-handler', ''));
            }

        },

        _handleClick: function (node) {
            if ($(node).next().hasClass("subnodes")) {
                $(node).removeClass("opened");
                $(node).next().hide().remove();
            } else {
                $(node).addClass("opened");
                this.loadNodes(node);
            }
        },

        _setOption: function (option, value) {
            $.Widget.prototype._setOption.apply(this, arguments);
            console.log("Option setted" + option);
        }
    });
})(jQuery);