package xjt.q;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Stack;
/**
 * https://kaiwu.lagou.com/course/courseInfo.htm?courseId=685#/detail/pc?id=6690
 * */
@Slf4j
public class StackQ {
    /**
     *【题目】字符串中只有字符'('和')'。合法字符串需要括号可以配对。比如：
     * 输入："()"
     * 输出：true
     * 解释：()，()()，(())是合法的。)(，()(，(()是非法的。
     * */
    boolean validStr(String s){
        if(StringUtils.isBlank(s)){
            return true;
        }
        s = s.trim();
        if (s.length() % 2 == 1) {
            return false;
        }
        Stack<Character> t = new Stack<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('){
                t.push(c);
            }else if(c==')'){
                if(t.empty()){
                    return false;
                }
                t.pop();
            }else{
                return false;
            }
        }
        return t.empty();
    }
    boolean vaildComStr(String s){
        if(StringUtils.isBlank(s)){
            return true;
        }
        s = s.trim();
        if(s.length()%2==1){
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='('||c=='[' ||c=='{'){
                stack.push(c);
            }else if(c == ')'){
                if(stack.empty()||stack.peek()!='('){
                    return false;
                }
                stack.pop();
            }else if(c==']'){
                if(stack.empty()||stack.peek()!='['){
                    return false;
                }
                stack.pop();
            }else if(c=='}'){
                if(stack.empty()||stack.peek()!='{'){
                    return false;
                }
                stack.pop();
            }else{
                return false;
            }
        }
        return stack.empty();
    }

    public static void main(String[] args) {
        StackQ stackQ = new StackQ();
        String s1 = "()()";
        String s2 = "(())()";
        String s3 = "(()())()";
        String s4 = "((()())()";
        log.info("valid`s1={}`r={}",s1,stackQ.validStr(s1));
        log.info("valid`s2={}`r={}",s2,stackQ.validStr(s2));
        log.info("valid`s3={}`r={}",s3,stackQ.validStr(s3));
        log.info("valid`s4={}`r={}",s4,stackQ.validStr(s4));

        String s5 = "[((()()))]{({})}[]";
        log.info("valid`s5={}`r={}",s5,stackQ.vaildComStr(s5));

    }
}
