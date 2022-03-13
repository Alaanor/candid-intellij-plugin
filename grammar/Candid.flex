package com.github.alaanor.candid.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

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

EOL=\R
WHITE_SPACE=\s+

ID=[_a-zA-Z][_a-zA-Z0-9]*
DIGIT=[0-9]
HEX=[0-9a-fA-F]
ASCII=[\x20-\x21\x23-\x5b\x5d-\x7e]

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "="                { return OP_EQ; }
  "type"             { return TYPE; }
  "bool"             { return BOOL; }
  "text"             { return TEXT; }
  "null"             { return NULL; }
  "reserved"         { return RESERVED; }
  "empty"            { return EMPTY; }
  "principal"        { return PRINCIPAL; }
  "nat"              { return NAT; }
  "nat8"             { return NAT8; }
  "nat16"            { return NAT16; }
  "nat32"            { return NAT32; }
  "nat64"            { return NAT64; }
  "int"              { return INT; }
  "int8"             { return INT8; }
  "int16"            { return INT16; }
  "int32"            { return INT32; }
  "int64"            { return INT64; }
  "float32"          { return FLOAT32; }
  "float64"          { return FLOAT64; }
  "opt"              { return OPT; }
  "vec"              { return VEC; }
  "record"           { return RECORD; }
  "variant"          { return VARIANT; }
  "func"             { return FUNC; }
  "service"          { return SERVICE; }
  "functype"         { return FUNCTYPE; }
  "actortype"        { return ACTORTYPE; }

  {ID}               { return ID; }
  {DIGIT}            { return DIGIT; }
  {HEX}              { return HEX; }
  {ASCII}            { return ASCII; }

}

[^] { return BAD_CHARACTER; }
