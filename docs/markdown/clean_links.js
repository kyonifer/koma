(function() {
    function adjust(selector, callback) {
        var elements = document.querySelectorAll(selector);
        for (var ii = 0; ii < elements.length; ++ii)
            callback(elements[ii])
    }

    function adjustText(selector, callback) {
        adjust(selector, function(element) {
            if (element.childNodes.length == 1) {
                var textNode = element.childNodes[0];
                if (3 == textNode.nodeType)
                    textNode.data = callback(textNode.data, element);
            }
        });
    }

    function applyFixes() {
        // Try to clean up dokka's name mangling
        adjustText('.dropdown-submenu li > a', function(text) {
            return text.replace(/(^| )\w/g, function(m) {
                return m[m.length - 1].toUpperCase();
            });
        });

        adjustText('.dropdown-submenu li > a', function(text, a) {
            var hyphenated = a.href.replace(/\/(index.html)?$/, '').replace(/.*[\/#]/, '');
            if (hyphenated == "-init-")
                return "<init>"
            else if (hyphenated.replace(/[_\W]/g, '') == text.replace(/[_\W]/g, '').toLowerCase())
                return hyphenated.replace(/-\w/g, function(m) {
                    return m[1].toUpperCase();
                });
            else return text;
        });
    }

    function ready() {
        applyFixes();
        window.removeEventListener('DOMContentLoaded', ready);
    }
    window.addEventListener('DOMContentLoaded', ready);
})();
