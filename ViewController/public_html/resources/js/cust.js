function menuAction(evt) {
    var menu = evt.getSource();
    AdfCustomEvent.queue(menu, "menuClick", {},true);
    evt.cancel();
}

function tableDblClick(evt) {
    var menu = evt.getSource();
    AdfCustomEvent.queue(menu, "tableDblClick",{},true);
    evt.cancel();
}

function enforcePreventUserInput(evt) {
    var popup = AdfPage.PAGE.findComponentByAbsoluteId('running');
    if (popup != null) {
        AdfPage.PAGE.addBusyStateListener(popup, handleBusyState);
        evt.preventUserInput();
    }
}
//JavaScript callback handler
function handleBusyState(evt) {
    var popup = AdfPage.PAGE.findComponentByAbsoluteId('running');
    if (popup != null) {
        if (evt.isBusy()) {
            popup.show();
        }
        else {
            popup.hide();//remove busy state listener (important !!!) 
            AdfPage.PAGE.removeBusyStateListener(popup, handleBusyState);
        }
    }
}