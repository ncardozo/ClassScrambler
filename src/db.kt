import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import Student
import com.google.gson.reflect.TypeToken

/*
    fun main(args : Array<String>) {
        generateDatabase()
    }
*/

fun loadCourse(course : String) : MutableList<Student> {
    val studentJSONList = File("./files/$course.json").readText()
    val gson = Gson()
    val listType = object : TypeToken<List<Student>>() {}.type
    return gson.fromJson(studentJSONList, listType)
}

fun saveCourse(course : String, students : List<Student>) {
    val gameFile = File("./files/$course.json")
    gameFile.writeText("")
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    gameFile.appendText(gsonPretty.toJson(students))
}

fun generateDatabase(): Unit {
        //val In = BufferedReader(InputStreamReader(System.`in`))
        //print("Enter the file of your class:")
        val fileName = "isis1106"//In.readLine()!!
        val courseFile = File("./files/$fileName.txt")
        val gameFile = File("./files/$fileName.json")
        val students = mutableListOf<Student>()//MutableList<Student>()
        if (courseFile.exists()) {
            gameFile.createNewFile()
            courseFile.forEachLine {
                val values = it.split(",")
                val s = Student(values[1], values[0])
                students.add(s)
            }
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            gameFile.appendText(gsonPretty.toJson(students))

        } else
            print("File not found")
    }