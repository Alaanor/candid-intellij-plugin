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

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "="                { return OP_EQ; }
  "type"             { return KW_TYPE; }

  {ID}               { return ID; }

}

[^] { return BAD_CHARACTER; }
