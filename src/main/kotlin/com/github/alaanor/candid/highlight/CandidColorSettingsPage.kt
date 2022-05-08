package com.github.alaanor.candid.highlight

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class CandidColorSettingsPage : ColorSettingsPage {
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> =
        CandidColor.values().map { it.attributesDescriptor }.toTypedArray()

    override fun getDisplayName(): String = "Candid"
    override fun getIcon(): Icon? = null
    override fun getHighlighter(): SyntaxHighlighter = CandidSyntaxHighlighter()
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null

    override fun getDemoText(): String =
        """
import "foo/bar.did";

type Name = text;

type Entry = record {
    person: Name;
    "age": nat;
};

service: {
    insert: (Name, nat) -> ();
    lookup: (Name) -> (opt Entry) query;
}
            """.trim()

}