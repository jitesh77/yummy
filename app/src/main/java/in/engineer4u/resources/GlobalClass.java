package in.engineer4u.resources;

/**
 * Created by Rupesh Choudhary on 7/7/2015.
 */
import android.app.Application;

public class GlobalClass extends Application{

    private String name;
    private String email;
    private String photoId;
    private int loginFlag;
    private String type="";

    public String getName() {

        return name;
    }

    public void setName(String aName) {

        name = aName;

    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String aEmail) {

        email = aEmail;
    }

    public String getPhotoId() {

        return photoId;
    }

    public void setPhotoId(String tphotoId) {

        photoId = tphotoId;
    }

    public int getLoginFlag() {

        return loginFlag;
    }

    public void setLoginFlag(int tloginFlag) {

        loginFlag = tloginFlag;
    }

    public String getType() {

        return type;
    }

    public void setType(String tType) {
      if(type=="restaurant" || type=="cafe" || type=="bar"  )
        type = tType;
        else
          type="";
    }

}