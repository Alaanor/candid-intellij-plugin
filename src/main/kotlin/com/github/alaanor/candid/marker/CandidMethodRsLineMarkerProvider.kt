package com.github.alaanor.candid.marker

import com.github.alaanor.candid.icon.CandidIcons
import com.github.alaanor.candid.psi.CandidMethodType
import com.github.alaanor.candid.psi.stub.index.CandidStubMethodIndex
import com.github.alaanor.candid.util.CandidRustIcCdkUtil
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.psi.PsiElement
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import org.rust.lang.core.psi.RsFunction

class CandidMethodRsLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val rsFunction = element as? RsFunction ?: return
        val name = CandidRustIcCdkUtil.getName(rsFunction) ?: return
        val candidMethod = StubIndex.getElements(
            CandidStubMethodIndex.Key,
            name,
            element.project,
            GlobalSearchScope.projectScope(element.project),
            CandidMethodType::class.java
        ).firstOrNull() ?: return

        // we found a matching rust method and candid method

        val builder = NavigationGutterIconBuilder.create(CandidIcons.FileType)
            .setTarget(candidMethod)
            .setTooltipText("Navigate to the corresponding candid method")

        result.add(builder.createLineMarkerInfo(element.identifier))
    }
}