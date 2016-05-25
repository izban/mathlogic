package Task4.Util.Parser;

import Task4.Util.Tree.*;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public class Parser {
    private Lexer lexer;

    private Node parseNameLower() {
        Token cur = Token.type(lexer.curToken());
        if (cur == Token.VARIABLE) {
            Node res = new NodeVariable(lexer.curToken());
            lexer.nextToken();
            return res;
        } else {
            throw new AssertionError();
        }
    }

    private Node parseNameUpper() {
        Token cur = Token.type(lexer.curToken());
        if (cur == Token.PREDICATE) {
            Node res = new NodeVariable(lexer.curToken());
            lexer.nextToken();
            return res;
        } else {
            throw new AssertionError();
        }
    }

    private Node parseMultiplier() {
        Token cur = lexer.curType();
        Node res;
        switch (cur) {
            case VARIABLE:
                String name = lexer.curToken();
                lexer.nextToken();
                cur = Token.type(lexer.curToken());
                ArrayList<Node> a = new ArrayList<>();
                if (cur == Token.LEFT_BRACKET) {
                    lexer.nextToken();
                    while (true) {
                        a.add(parseTerm());
                        cur = Token.type(lexer.curToken());
                        if (cur == Token.COMMA) {
                            lexer.nextToken();
                        } else {
                            if (cur != Token.RIGHT_BRACKET) {
                                throw new AssertionError();
                            }
                            lexer.nextToken();
                            break;
                        }
                    }
                    Node ch[] = new Node[a.size()];
                    ch = a.toArray(ch);
                    res = new NodePredicat(name, ch);
                } else {
                    res = new NodeVariable(name);
                }
                break;
            case LEFT_BRACKET:
                lexer.nextToken();
                res = parseTerm();
                if (lexer.curType() != Token.RIGHT_BRACKET) {
                    throw new AssertionError();
                }
                lexer.nextToken();
                break;
            case ZERO:
                lexer.nextToken();
                res = new NodeZero();
                break;
            default:
                throw new AssertionError();
        }
        while (true) {
            cur = lexer.curType();
            if (cur == Token.APOSTROPHE) {
                res = new NodeInc(res);
                lexer.nextToken();
            } else break;
        }
        return res;
    }

    private Node parseSummand() {
        Node res = parseMultiplier();
        while (true) {
            Token cur = Token.type(lexer.curToken());
            if (cur == Token.MULTIPLY) {
                lexer.nextToken();
                res = new NodeMultiply(res, parseMultiplier());
                continue;
            }
            break;
        }
        return res;
    }

    private Node parseTerm() {
        Node res = parseSummand();
        while (true) {
            Token cur = Token.type(lexer.curToken());
            if (cur == Token.ADD) {
                lexer.nextToken();
                res = new NodeAdd(res, parseSummand());
                continue;
            }
            break;
        }
        return res;
    }

    private Node parsePredicate() {
        Token cur = Token.type(lexer.curToken());
        Node res;
        switch (cur) {
            case PREDICATE:
                String predicate = lexer.curToken();
                lexer.nextToken();
                cur = Token.type(lexer.curToken());
                ArrayList<Node> a = new ArrayList<>();
                if (cur == Token.LEFT_BRACKET) {
                    lexer.nextToken();
                    while (true) {
                        a.add(parseTerm());
                        cur = Token.type(lexer.curToken());
                        if (cur == Token.COMMA) {
                            lexer.nextToken();
                        } else {
                            if (cur != Token.RIGHT_BRACKET) {
                                throw new AssertionError();
                            }
                            lexer.nextToken();
                            break;
                        }
                    }
                    Node ch[] = new Node[a.size()];
                    ch = a.toArray(ch);
                    return new NodePredicat(predicate, ch);
                } else {
                    return new NodeVariable(predicate);
                }

            default:
                Node left = parseTerm();
                cur = lexer.curType();
                if (cur != Token.EQUAL) {
                    //System.err.println(left.toString());
                    //System.err.println(cur);
                    throw new AssertionError();// ¯\_(ツ)_/¯
                }
                lexer.nextToken();
                Node right = parseTerm();
                return new NodeEqual(left, right);
        }
    }

    private Node parseUnary() {
        Token cur = Token.type(lexer.curToken());
        Node res;
        switch (cur) {
            case NOT:
                lexer.nextToken();
                return new NodeNot(parseUnary());
            case LEFT_BRACKET:
                char c = lexer.afterPairCurBracket();
                if (c == '=' || c == '+' || c == '*' || c == '\'') {
                    return parsePredicate();
                }
                lexer.nextToken();
                res = parseExpression();
                if (Token.type(lexer.curToken()) != Token.RIGHT_BRACKET) {
                    throw new AssertionError();
                }
                lexer.nextToken();
                return res;
            case FORALL:
            case EXIST:
                Token quantor = cur;
                lexer.nextToken();
                Node var = parseNameLower();
                Node ch = parseUnary();
                if (quantor == Token.FORALL) {
                    return new NodeForall(var, ch);
                } else if (quantor == Token.EXIST) {
                    return new NodeExist(var, ch);
                } else assert false;
            default:
                return parsePredicate();
        }
    }

    private Node parseConjunction() {
        Node res = parseUnary();
        while (true) {
            Token cur = Token.type(lexer.curToken());
            if (cur == Token.AND) {
                lexer.nextToken();
                res = new NodeAnd(res, parseUnary());
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
                res = new NodeOr(res, parseConjunction());
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
