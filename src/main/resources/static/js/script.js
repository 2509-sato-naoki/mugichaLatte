$(document).ready(function() {
    $('tr').each(function() {
        var start = $(this).find('.startTime').text().trim();
        var end = $(this).find('.endTime').text().trim();
        var rest = parseInt($(this).find('.rest').text()) || 0; // 分単位

        if(start && end) {
            // HH:mm → 分に変換
            var startParts = start.split(':');
            var endParts = end.split(':');
            var startMinutes = parseInt(startParts[0], 10) * 60 + parseInt(startParts[1], 10);
            var endMinutes = parseInt(endParts[0], 10) * 60 + parseInt(endParts[1], 10);

            // 勤務時間 = end - start - rest
            var workMinutes = endMinutes - startMinutes - rest;
            if(workMinutes < 0) workMinutes = 0;

            // 時間と分に変換
            var hours = Math.floor(workMinutes / 60);
            var minutes = workMinutes % 60;

            // HH:mm フォーマットに整形（2桁表示）
            var formatted = ('0' + hours).slice(-2) + ':' + ('0' + minutes).slice(-2);

            $(this).find('.workTime').text(formatted); // 表示
        } else {
            $(this).find('.workTime').text('');
        }
    });
});
