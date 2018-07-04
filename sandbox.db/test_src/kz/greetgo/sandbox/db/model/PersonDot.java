package kz.greetgo.sandbox.db.model;

import kz.greetgo.sandbox.controller.model.PhoneType;
import kz.greetgo.sandbox.controller.model.UserInfo;

public class PersonDot {
  public String id;
  public String accountName, password;
  public boolean disabled = false;
  public String surname, name, patronymic;

  public String encryptedPassword;

  public UserInfo toUserInfo() {
    UserInfo ret = new UserInfo();
    ret.id = id;
    ret.accountName = accountName;
    ret.surname = surname;
    ret.name = name;
    ret.patronymic = patronymic;
    ret.phoneType = PhoneType.MOBILE;
    return ret;
  }

  public void showInfo() {
    System.out.println("----------: Init Person " + accountName + " with password " + password);
  }
}
