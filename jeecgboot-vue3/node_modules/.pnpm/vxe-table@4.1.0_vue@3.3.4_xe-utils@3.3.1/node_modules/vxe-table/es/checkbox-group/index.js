import VxeCheckboxGroupComponent from '../checkbox/src/group';
import { dynamicApp } from '../dynamics';
export var CheckboxGroup = Object.assign(VxeCheckboxGroupComponent, {
    install: function (app) {
        dynamicApp.component(VxeCheckboxGroupComponent.name, VxeCheckboxGroupComponent);
        app.component(VxeCheckboxGroupComponent.name, VxeCheckboxGroupComponent);
    }
});
export default CheckboxGroup;
