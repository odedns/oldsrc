package onjlib.command;

public interface HashCmdParams_SEI extends java.rmi.Remote
{
 public void setParam(java.lang.String name,java.lang.Object value);
 public java.lang.String toString();
 public void setCommandClassName(java.lang.String className);
 public java.lang.String getCommandClassName();
 public java.lang.Object getParam(java.lang.String name);
}