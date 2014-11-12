package service;

public class MyService
{
    public String getGreeting(String name)
    {
        return "hello " + name;
    }
    public void update(String data)
    {
        System.out.println("<" + data + "> 已经更新");
    }
}