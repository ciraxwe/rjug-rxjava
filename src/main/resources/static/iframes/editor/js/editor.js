/*
 * Copyright 2014 Volcra
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var themes = ['ace/theme/monokai', 'ace/theme/github', 'ace/theme/cobalt'];
var editor = ace.edit("editor");
editor.setTheme(themes[0]);
editor.setFontSize(20);
editor.getSession().setMode("ace/mode/groovy");
editor.commands.addCommand({
    name: 'Menu',
    bindKey: {win: 'Ctrl-M', mac: 'Command-M'},
    exec: function () {
        $('#open-button').trigger('click');
    }
});
editor.commands.addCommand({
    name: 'Esc',
    bindKey: {win: 'Esc', mac: 'Esc'},
    exec: function () {
        $('#console').removeClass('show');
        $(document.body).removeClass('show-menu');
    }
});
editor.commands.addCommand({
    name: 'Alter Theme Right',
    bindKey: {win: 'Alt+]', mac: 'Alt+]'},
    exec: function () {
        var theme = themes.shift()
        editor.setTheme(theme);
        themes.push(theme);
    }
});
editor.commands.addCommand({
    name: 'Alter Theme Left',
    bindKey: {win: 'Alt+[', mac: 'Alt+['},
    exec: function () {
        var theme = themes.pop()
        editor.setTheme(theme);
        themes.unshift(theme);
    }
});
editor.commands.addCommand({
    name: 'Run',
    bindKey: {win: 'Ctrl-Enter', mac: 'Command-Enter'},
    exec: function () {
        var c = $('#console');
        c.find('div').html('');
        c.find('pre').html('');

        $('#spinner').fadeIn(400);
        $.post('http://localhost:8080/script', {script: editor.getSession().doc.getValue()}, function (data) {
            c.find('#executionTime').html(new Date(data.endTime) - new Date(data.start) + ' ms');
            c.find('#output').html(data.output);
            c.find('#error').html(data.error);

            if (data.error) {
                c.css('height', '50%');
            } else{
                c.css('height', '40%');
            }

            c.addClass('show');
        })
            .always(function () {
                $('#spinner').fadeOut(200);
            });
    }
});

$('.menu .icon-list a').click(function () {
    editor.getSession().doc.setValue($('#' + $(this).attr('id') + '-code').text());
});

var opts = {
    lines: 13, // The number of lines to draw
    length: 40, // The length of each line
    width: 14, // The line thickness
    radius: 16, // The radius of the inner circle
    corners: 1, // Corner roundness (0..1)
    rotate: 0, // The rotation offset
    direction: 1, // 1: clockwise, -1: counterclockwise
    color: '#000', // #rgb or #rrggbb or array of colors
    speed: 1, // Rounds per second
    trail: 60, // Afterglow percentage
    shadow: true, // Whether to render a shadow
    hwaccel: false, // Whether to use hardware acceleration
    className: 'spinner', // The CSS class to assign to the spinner
    zIndex: 2e9, // The z-index (defaults to 2000000000)
    top: '50%', // Top position relative to parent
    left: '50%' // Left position relative to parent
};

var target = document.getElementById('spinner');
var spinner = new Spinner(opts).spin(target);
