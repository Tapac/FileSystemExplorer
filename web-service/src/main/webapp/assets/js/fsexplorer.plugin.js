(function ($) {
    $.widget('ui.fsexplorer', {
        options: {
            serviceUrl: "http://" + window.location.host +  "/service/",
            render: "server"
        },
        loadNodes: function (node) {
            var renderService = "rendered";

            if (this.options.render == "server") {
                var toAppend = (node.attr('fullpath') == "") ? node : node.after('<li class="subnodes"></li>').next();
                var url = this.options.serviceUrl + renderService + "/children/" + $(node).attr('fullpath');
                toAppend.load(encodeURI(url));
            }
        },
        _create: function () {
            var self = this,
                el = self.element;
            $(el).addClass("fsexplorer");
            $(el).on("click", "li.folder, li.arc", function() {self._handleClick($(this))});
            this.loadNodes($(el).attr('fullpath', ''));
        },

        _handleClick: function(node) {
            if($(node).next().hasClass("subnodes")) {
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