package com.softeq.constant;

import java.util.Properties;

public final class ConstantConfig {
    private Properties properties;
    private static ConstantConfig instance = null;

    private ConstantConfig (){
        this.properties = new Properties();
        try{
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constant.CONSTANT_CONF_LINK));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private synchronized static void createInstance () {
        if (instance == null) {
            instance = new ConstantConfig();
        }
    }

    // Uses singleton pattern to guarantee the creation of only one instance
    public static ConstantConfig getInstance(){
        if(instance == null) {
            createInstance();
        }
        return instance;
    }

    public String getProperty(String key){
        String result = null;
        if(key !=null && !key.trim().isEmpty()){
            result = this.properties.getProperty(key);
        }
        return result;
    }

    /* Override the clone method to ensure the "unique instance" of class*/
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
