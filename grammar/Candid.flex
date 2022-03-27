package com.github.alaanor.candid.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.github.alaanor.candid.CandidTypes;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.github.alaanor.candid.CandidTypes.*;

%%

%public
%class CandidLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

WHITE_SPACE = \s+

id = [_a-zA-Z][_a-zA-Z0-9]*
digit = [0-9]
escape = \\ [nrt\\\"\']

line_comment = ("//" [^\n]*)
block_comment = "/*" !([^]* "*/" [^]*) "*/"?

hex = [0-9a-fA-F]
hex_num = {hex} ("_"? {hex})*
hex_0x = "0x" {hex_num}
hex_escape_short = \\ {hex} {hex}
hex_escape_long = \\u\{ {hex_num} \}

ascii = [\x20-\x21\x23-\x5b\x5d-\x7e]
utf8enc = [\u0080-\u07ff]
        | [\u0800-\u0fff]
        | [\u1000-\ucfff]
        | [\ud000-\ud7ff]
        | [\ue000-\uffff]
        | [\U010000-\U03ffff]
        | [\U040000-\U0fffff]
        | [\U100000-\U10ffff]

%state STRING_LITERAL
%state LINE_COMMENT
%state BLOCK_COMMENT

%%

<STRING_LITERAL> {
  \"                    { yybegin(YYINITIAL); return DOUBLE_QUOTE; }
  {escape}              { return ESCAPE; }
  {hex_escape_short}    { return HEX_ESCAPE_SHORT; }
  {hex_escape_long}     { return HEX_ESCAPE_LONG; }
  {ascii}               { return ASCII; }
  {utf8enc}             { return UTF8ENC; }
}

<LINE_COMMENT> {
    {line_comment}  { yybegin(YYINITIAL); return CandidTypes.LINE_COMMENT; }
}

<BLOCK_COMMENT> {
    {block_comment} { yybegin(YYINITIAL); return CandidTypes.BLOCK_COMMENT; }
}

<YYINITIAL> {
  {WHITE_SPACE}     { return WHITE_SPACE; }

  "="               { return OP_EQ; }
  "type"            { return TYPE; }
  "bool"            { return BOOL; }
  "text"            { return TEXT; }
  "null"            { return NULL; }
  "reserved"        { return RESERVED; }
  "empty"           { return EMPTY; }
  "principal"       { return PRINCIPAL; }
  "nat"             { return NAT; }
  "nat8"            { return NAT8; }
  "nat16"           { return NAT16; }
  "nat32"           { return NAT32; }
  "nat64"           { return NAT64; }
  "int"             { return INT; }
  "int8"            { return INT8; }
  "int16"           { return INT16; }
  "int32"           { return INT32; }
  "int64"           { return INT64; }
  "float32"         { return FLOAT32; }
  "float64"         { return FLOAT64; }
  "opt"             { return OPT; }
  "vec"             { return VEC; }
  "record"          { return RECORD; }
  "variant"         { return VARIANT; }
  "func"            { return FUNC; }
  "service"         { return SERVICE; }
  "oneway"          { return ONEWAY; }
  "query"           { return QUERY; }

  ";"               { return SEMICOLON; }
  ","               { return COMMA; }
  ":"               { return COLON; }
  "{"               { return LBRACE; }
  "}"               { return RBRACE; }
  "("               { return LPAREN; }
  ")"               { return RPAREN; }
  "_"               { return UNDERSCORE; }
  "->"              { return LARROW; }

  {id}              { return ID; }
  {digit}           { return DIGIT; }
  {hex_0x}           { return HEX_0X; }

  \"                { yybegin(STRING_LITERAL); return DOUBLE_QUOTE; }
  "//"              { yypushback(2); yybegin(LINE_COMMENT); }
  "/*"              { yypushback(2); yybegin(BLOCK_COMMENT); }
}

[^] { return BAD_CHARACTER; }
