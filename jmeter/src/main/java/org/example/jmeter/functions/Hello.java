package org.example.jmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/6/2 14:08
 */
public class Hello extends AbstractFunction {

    //函数名称，按默认使用双下划线开头
    private static final String KEY = "__hello";

    //自定义函数参数列表说明, 定义完成后由getArgumentDesc()返回
    private static List<String> args = new LinkedList<>();

    static {
        //通过add方法可以添一个参数. 需添加多个参数时，多次调用即可添加多个参数
        args.add("参数1: 用户名");
        args.add("参数2: 性别");
    }

    //自定义的变量用于自定义函数调用时接收传入的参数
    //全局变量
    public String username;
    public String sex;


    //自定义函数的主体部份
    @Override
    public String execute(SampleResult sampleResult, Sampler sampler)
        throws InvalidVariableException {
        return username + "__" + sex;
    }

    //该方法用来接收用户调用函数时传入的参数
    @Override
    public void setParameters(Collection<CompoundVariable> collection)
        throws InvalidVariableException {
        //下面为固定写法
        Object[] values = collection.toArray();

        //强制将数组第一个元素提取为string并赋值给username
        username = ((CompoundVariable) values[0]).execute();
        sex = ((CompoundVariable) values[1]).execute();
    }

    //获取jmeter自定函数名称方法
    @Override
    public String getReferenceKey() {
        return KEY;
    }

    //该方法用来定义函数参数
    //并且会将参数描述显示在jmeter界面中
    @Override
    public List<String> getArgumentDesc() {
        return args;
    }
}
