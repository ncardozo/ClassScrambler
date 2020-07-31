class Student {
    var id = ""
    var name = ""
    var call = true
    var skip = true
    var guess = true

    constructor() : super() {}
    constructor(id:String = "", name:String = "") :super() {
        this.id = id
        this.name = name
    }

    fun clear() : Unit {
        this.call = true
        this.skip = true
        this.guess = true
    }
    fun useCall(): Unit {
        call = false
    }

    fun useSkip(): Unit {
        skip = false
    }

    fun useGuess(): Unit {
        guess = false
    }
}

class StudentModel