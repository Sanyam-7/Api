package  com.example.api
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import java.io.IOException

class GenerateTestCasesAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = e.project
        project?.let {
            try {
                ApiClient.generateTestCases(it)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }
}
