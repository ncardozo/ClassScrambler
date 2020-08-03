import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.chart.PieChart
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.util.Duration
import tornadofx.*
import java.net.URI
import javax.swing.text.IconView
import kotlin.random.Random
import kotlin.system.exitProcess
import javax.swing.text.html.ImageView as ImageView

var students = mutableListOf<Student>().asObservable()
var activeCourse = ""


class Interface : View("Class Scrambler") {

    val topView = find(MenuView::class)
    val bottomView = find(ControlsView::class)

    override val root = borderpane {
        top = topView.root
        bottom = bottomView.root
    }
}

class MenuView : View() {
    val otherView = find(ControlsView::class)

    override val root = menubar {
        menu("File") {
            menu("Load course") {
                item("LyM").action {
                    println("Loading...")
                    activeCourse = "isis1106"
                    students.clear()
                    students.addAll(loadCourse(activeCourse))
                    otherView.clearData()
                    otherView.loadData(students.toList())
                    println("Loaded $students")
                }
                item("Paradigms").action {
                    println("Loading isis4217...")
                    activeCourse = "isis4217"
                    students.clear()
                    students.addAll(loadCourse(activeCourse))
                    otherView.clearData()
                    otherView.loadData(students.toList())
                    println("Loaded $students")
                }
            }
            item("CreateCourse").action {
                openInternalBuilderWindow("Course name", modal = true, overlayPaint =  Color.DARKRED) {
                    vbox(20) {
                        val tf = textfield()
                        button("create").action {
                            generateDatabase(tf.text)
                        }
                    }
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
                exitProcess(0)
            }
        }

    }
}

class ControlsView : View() {
    private var rand = Random(16)
    private var student = Student()

    var studentlabel = label("Student") {
        useMaxWidth = true
        font = Font("Verdana", 20.0)
        alignment = Pos.CENTER
    }
    val piechartData : ObservableList<PieChart.Data> = observableListOf()
    //var wheel = piechart(data = piechartData)
    fun clearData() {
        piechartData.clear()
    }

    fun loadData(students : List<Student>) {
        piechartData.remove(0, piechartData.size)
        println(piechartData)
        students.forEach {
                piechartData.add(PieChart.Data(it.name, 1.0))
        }
    }

    override val root = vbox {
        piechart(data = piechartData) {
            val wheel = this
            isLegendVisible = false
            setOnMouseClicked {
                val index = rand.nextInt(students.size - 1)
                student = students[index]
                timeline {
                    keyframe(Duration.seconds(1.0)) {
                        keyvalue(wheel.rotateProperty(), index*360)
                    }
                }
                println(student)
                studentlabel.text = student.name
            }
        }/*.setOnMouseClicked {

        }*/
        button("Get student") {
            useMaxHeight = true
            action {
                student = students.get(rand.nextInt(students.size - 1))
                println(student)
                studentlabel.text = student.name
            }
        }
        borderpane {
            top =  studentlabel
            center = hbox(spacing = 10.0) {
                button("${student.skip}", imageview("resources/skipIcon.png")) {
                    action {
                        student.useSkip()
                        println("${student.name} used the skip")

                        studentlabel.text = student.name
                    }
 //                   disableWhen { student.skip.observable() }
                }
                button("${student.call}", imageview("resources/callIcon.png")) {
                    action {
                        student.useCall()
                        println("${student.name} called a friend")
                    }
                }
                button("${student.guess}", imageview("resources/guessIcon.png")) {
                    action {
                        student.useGuess()
                        println("${student.name} is clueless")
                    }
                }
            }
        }
    }
}