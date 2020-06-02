package webservices;

public interface WSBean_SEI extends java.rmi.Remote
{
 public java.util.HashMap execute(java.util.HashMap params);
 public void setData(java.lang.String data);
 public java.lang.String getData();
}