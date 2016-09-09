$(document).ready(function () {
    check();
});

function check() {
    $.get('check', function (hosts) {
        var html = '<h1>' + hosts.name + '</h1>';
        hosts.group.forEach(function (group) {
            html += '<h2>' + group.name + '</h2>';
            group.host.forEach(function (host) {
                var color = host.status > 200 ? 'error' : 'success';
                html += '<table><tr class="' + color + '">';
                html += '<td width="200">' + host.name + '</td>';
                html += '<td width="300">' + host.url + '</td>';
                html += '<td width="100">' + host.status + '</td>';
                html += '<td width="100" align="right">' + host.time + ' ms</td>';
                html += '</tr></table>';
            });
        });
        html += "<br>";
        
        html += '<p>Last refresh: ' + $.format.date(new Date(), "dd.MM.yyyy HH:mm:ss.SSSk") + '</p>';

        $('#content').html(html);
    });
}

var interval;
var autorefresh = false;

function switchAutoRefresh() {
    autorefresh = !autorefresh;

    var text = autorefresh ? 'Toggle auto refresh off' : 'Toggle auto refresh on';
    $('#autorefresh').text(text);

    if (autorefresh) {
        interval = setInterval(function () {
            check();
        }, 10000);
    } else {
        clearInterval(interval);
    }
}
