function menuAction(evt) {
    var menu = evt.getSource();
    AdfCustomEvent.queue(menu, "menuClick",{ },true);
    evt.cancel();
}

function tableDblClick(evt) {
    var menu = evt.getSource();
    AdfCustomEvent.queue(menu, "tableDblClick",{ },true);
    evt.cancel();
}