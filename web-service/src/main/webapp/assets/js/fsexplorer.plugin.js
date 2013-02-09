(function ($) {
    $.widget('ui.fsexplorer', {
        options: {
            serviceUrl: "http://" + window.location.host + "/service/",
            render: "server",
            template: ""
        },
        loadNodes: function (node) {
            var o = this.options;
            var renderService = "rendered/";
            var toAppend = (node.attr('fullpath') == "") ? node : node.after('<li class="subnodes"></li>').next();

            if (o.render == "server") {
                var url = this.options.serviceUrl + renderService + "children/" + $(node).attr('fullpath');
                toAppend.load(encodeURI(url));
            } else {
                $.getJSON(this.options.serviceUrl + "children/" + $(node).attr('fullpath'),
                    function (json) {
                       toAppend.html(o.template(json));
                    });

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
                        self.loadNodes($(el).attr('fullpath', ''));
                    });
            } else {
                this.loadNodes($(el).attr('fullpath', ''));
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