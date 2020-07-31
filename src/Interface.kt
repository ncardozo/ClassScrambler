import tornadofx.*
import tornadofx.Stylesheet.Companion.label
import kotlin.random.Random

var students = mutableListOf<Student>()
var activeCourse = ""

class Interface : View() {

    val topView = find(MenuView::class)
    val bottomView = find(ControlsView::class)

    override val root = borderpane {
        top = topView.root
        bottom = bottomView.root
    }
}

class MenuView : View() {
    override val root = menubar {
        menu("File") {
            menu("Load course") {
                item("LyM").action {
                    println("Loading...")
                    activeCourse = "isis1106"
                    students = loadCourse(activeCourse)
                }
                item("Paradigms").action {
                    println("Loading...")
                    activeCourse = "isis4217"
                    students = loadCourse(activeCourse)
                }
            }
            item("Restart week").action {
                students.map { 
                    it.clear()
                }
                saveCourse(activeCourse, students)
            }
            item("Save",  "Shortcut+S").action {
                saveCourse(activeCourse, students)
                println("Saving")
            }
            item("Quit", "Shortcut+Q").action {
                println("Quitting")
                saveCourse(activeCourse, students)
            }
        }

    }
}

class ControlsView : View() {
    private var rand = Random(16)
    private var student = Student()

    override val root = hbox {
        button("Get student") {
            action {
                student = students.get(rand.nextInt(students.size - 1))
                println(student)
            }
        }
        borderpane {
            top = label(student.name)
            center = hbox {
                button("Skip") {
                    action {
                        student.useSkip()
                        println("${student.name} used the skip")
                    }
                }
                button("Call a friend") {
                    action {
                        student.useCall()
                        println("${student.name} called a friend")
                    }
                    enableWhen(student.call)
                }
                button("Shot in the dark") {
                    action {
                        student.useGuess()
                        println("${student.name} is clueless")
                    }
                }
            }
        }
    }
}