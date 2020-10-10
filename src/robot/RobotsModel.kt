package robot

import mvc.Model

class RobotsModel: Model() {
    var robots: List<Robot> = listOf()
        set(value) { field = value; update() }

    fun add(robot: Robot) {
        robots = robots.plus(robot)
    }
}