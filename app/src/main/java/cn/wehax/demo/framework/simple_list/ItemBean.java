package cn.wehax.demo.framework.simple_list;

import android.os.Bundle;

class ItemBean {
    String title;
    String des;
    Class<?> targetClazz;
    Bundle bundle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Class<?> getTargetClazz() {
        return targetClazz;
    }

    public void setTargetClazz(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}