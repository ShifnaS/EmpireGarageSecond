package info.apatrix.empiregarage.utils;

import info.apatrix.empiregarage.model.ServicePackagesTask;

public interface OnItemSelectListner {

    void onclick(ServicePackagesTask list);

    void getMaterialID(String value);
    void getQuantity(String value);

}
