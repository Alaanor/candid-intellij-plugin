package com.github.alaanor.candid.listener

import com.github.alaanor.candid.project.DfxJson
import com.intellij.openapi.vfs.AsyncFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent

class DfxFileListener : AsyncFileListener {
    override fun prepareChange(events: MutableList<out VFileEvent>): AsyncFileListener.ChangeApplier? {
        var hasInterestingEvent = false

        for (event in events)
            if (event.path.endsWith("dfx.json"))
                hasInterestingEvent = true

        if (!hasInterestingEvent) return null

        return object : AsyncFileListener.ChangeApplier {
            override fun afterVfsChange() {
                DfxJson.updateCache()
            }
        }
    }
}