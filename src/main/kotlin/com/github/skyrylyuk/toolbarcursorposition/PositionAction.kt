@file:Suppress("UnstableApiUsage")

package com.github.skyrylyuk.toolbarcursorposition

import com.github.skyrylyuk.toolbarcursorposition.ToolBarCursorMessageBundle.message
import com.intellij.ide.util.EditorGotoLineNumberDialog
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.ex.ActionUtil


import com.intellij.openapi.util.IconLoader.getIcon
import javax.swing.Icon


class PositionAction : AnAction() {

    override fun update(event: AnActionEvent) {
        event.getData(CommonDataKeys.EDITOR)?.caretModel?.let { caretModel ->

            val message = when (caretModel.caretCount) {
                0 -> ""
                1 -> caretModel.logicalPosition.let { "${it.line + 1}:${it.column + 1}" }
                else -> "${caretModel.caretCount} carets"
            }

            event.presentation.apply {
                text = message
                description = descriptionMessage
                putClientProperty(ActionUtil.SHOW_TEXT_IN_TOOLBAR, true)
                icon = null
            }
        }
    }

    override fun actionPerformed(event: AnActionEvent) {
        EditorGotoLineNumberDialog(
            event.getData(CommonDataKeys.PROJECT),
            event.getData(CommonDataKeys.EDITOR)
        ).show()
    }

    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT

}

val descriptionMessage = message("toolbar.cursor.description")

object ToolBarCursorIcon {
    val PLUGIN_ICON: Icon = getIcon("/icons/pluginIcon.svg", ToolBarCursorIcon::class.java)
}

