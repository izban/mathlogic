package Util.Parser;

import Util.Tree.*;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public class Parser {
    private Lexer lexer;

    private Node parseNegate() {
        Token cur = Token.type(lexer.curToken());
        Node res;
        switch (cur) {
            case VARIABLE:
                res = new NodeVariable(lexer.curToken());
                lexer.nextToken();
                return res;
            case NOT:
                lexer.nextToken();
                return new NodeNot(parseNegate());
            case LEFT_BRACKET:
                lexer.nextToken();
                res = parseExpression();
                assert Token.type(lexer.curToken()) == Token.RIGHT_BRACKET;
                lexer.nextToken();
                return res;
            default:
                throw new AssertionError();
        }
    }

    private Node parseConjunction() {
        Node res = parseNegate();
        while (true) {
            Token cur = Token.type(lexer.curToken());
            if (cur == Token.AND) {
                lexer.nextToken();
                res = new NodeAnd(res, parseDisjunction());
                continue;
            }
            break;
        }
        return res;
    }

    private Node parseDisjunction() {
        Node res = parseConjunction();
        while (true) {
            Token cur = Token.type(lexer.curToken());
            if (cur == Token.OR) {
                lexer.nextToken();
                res = new NodeOr(res, parseDisjunction());
                continue;
            }
            break;
        }
        return res;
    }

    private Node parseExpression() {
        Node res = parseDisjunction();
        Token cur = Token.type(lexer.curToken());
        if (cur == Token.IMPLICATION) {
            lexer.nextToken();
            return new NodeImpl(res, parseExpression());
        }
        return res;
    }

    private ArrayList<Node> parseHeader() {
        ArrayList<Node> res = new ArrayList<>();
        while (true) {
            Token cur = Token.type(lexer.curToken());
            if (cur == Token.END) {
                break;
            }
            lexer.nextToken();
            res.add(parseExpression());
        }
        return res;
    }

    public Node parseTree(String s) {
        lexer = new Lexer(s);
        return parseExpression();
    }

    public ArrayList<Node> parseHeader(String s) {
        lexer = new Lexer(s);
        return parseHeader();
    }
}
