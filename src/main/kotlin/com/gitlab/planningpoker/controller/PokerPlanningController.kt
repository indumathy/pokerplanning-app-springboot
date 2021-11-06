package com.gitlab.planningpoker.controller

import com.gitlab.planningpoker.dto.IssuesInEstimationDto
import com.gitlab.planningpoker.dto.UserDto
import com.gitlab.planningpoker.dto.VotedUsersCountDto
import com.gitlab.planningpoker.exception.ResourceNotFoundException
import com.gitlab.planningpoker.model.Employee
import com.gitlab.planningpoker.repository.EmployeeRepository
import com.gitlab.planningpoker.repository.UserRepository
import org.gitlab4j.api.Constants
import org.gitlab4j.api.GitLabApi
import org.gitlab4j.api.GitLabApiException
import org.gitlab4j.api.models.IssueFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/api/v1/")
class PokerPlanningController(val userRepository: UserRepository) {
    @Autowired
    private val employeeRepository: EmployeeRepository? = null

    @GetMapping("/pokerplanning/gitlab")
    @Throws(GitLabApiException::class)
    fun getGitData(): MutableList<IssuesInEstimationDto> {
        println("going to execute gitlab api..")
        val gitLabApi = GitLabApi.oauth2Login("https://gitlab2.vacos.de/", "indumathy.balaraman@vacos.de", "Rohit@2011")

        gitLabApi.issuesApi.getIssues(50).forEach { println("....eeeeeeee...." + it.size) }
        val api = gitLabApi.boardsApi

        val board = api.getBoard(30, 10)

        val labelInEstimation = board.lists.forEach {
            //     println("...label. mmmm...." + it.label.name)
        }
        val filter = IssueFilter().apply {
            scope = Constants.IssueScope.ALL
            labels = mutableListOf("process::In Estimation")
            state = Constants.IssueState.OPENED
        }
        val allIssue = gitLabApi.issuesApi.getIssues(30, filter)
        //println(".....issues.issues..............222222222222........." + allIssue.size + "..." + gitLabApi.projectApi.getProject(30).name)

        val Valueis = allIssue.map { it ->
            var issue = IssuesInEstimationDto().apply {
                this.id = it.id
                this.iid = it.iid
                this.title = it.title
                this.labels = it.labels
            }

            //   println(".....all issue...>..." + it.actualId + "... ." + it.iid + "..." + it.labels)
            issue
        }.toMutableList()


        val currentUser = gitLabApi.userApi.currentUser

        System.out.println(currentUser.username)
        return Valueis
    }

    @PutMapping("/pokerplanning/gitlab/assignAsModerator/{username}")
    fun assignAsModerator(@PathVariable  username: String): ResponseEntity<Boolean> {
        println("...assignAsModerator......................id.........................." + username)
        val users = userRepository.findAll()
        users?.map {
            it?.isModerator = (it?.username == username)
        }?.toMutableList()
        userRepository.saveAll(users)
        return ResponseEntity.ok(true)
    }

    @PutMapping("/pokerplanning/gitlab/removeFromModerator/{username}")
    fun removeFromModerator(@PathVariable username: String): ResponseEntity<Boolean> {
        println(".........................id.........................." + username)
        val user = userRepository.findByUsername(username).orElseThrow {
            ResourceNotFoundException(
                "Employee not exist with id :$username"
            )
        }
        if (user != null) {
            user.isModerator = false

            userRepository.save(user)
        }

        return ResponseEntity.ok(true)
    }

    @GetMapping("/pokerplanning/gitlab/users")
    @Throws(GitLabApiException::class)
    fun getUsers(): MutableList<UserDto> {
        println("going to execute gitlab api..")

        val users = userRepository?.findAll()
        val dto = users?.mapNotNull {
            UserDto(
                id = it?.id,
                userName = it?.username,
                isModerator = it?.isModerator,
                voted = !(it?.votedPoint.isNullOrEmpty()),
                votedPoint = it?.votedPoint
            )
        }
            ?.toMutableList() ?: mutableListOf()

        dto.forEach { println(".....................user.................." + it.userName + "..." + it.isModerator + "..." + it.voted + "...." + it.votedPoint) }

        return dto
    }



    @GetMapping("/pokerplanning/gitlab/votedusercounts")
    @Throws(GitLabApiException::class)
    fun getVotedUserCounts(): VotedUsersCountDto {
        println("going to execute gitlab api..")

        val users = userRepository?.findAll()
        val userCount = users?.size ?: 0
        val votedUsers = users?.filter { !it?.votedPoint.isNullOrEmpty() }?.size ?: 0
        var percentage = 0
        if (votedUsers > 0) {

            percentage = votedUsers * 100 / userCount
        }

        return VotedUsersCountDto(totalUserCount = userCount, votedUserCount = votedUsers, percentage)

    }

    // get all employees
    @get:GetMapping("/pokerplanning")
    val allEmployees: MutableList<Employee?>
        get() = employeeRepository!!.findAll()

    // create employee rest api
    @PostMapping("/pokerplanning")
    fun createEmployee(@RequestBody employee: Employee): Employee {

        return employeeRepository!!.save(employee)
    }

    // get employee by id rest api
    @GetMapping("/pokerplanning/{id}")
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<Employee> {
        //    getGitData(30)
        val employee = employeeRepository!!.findById(id)
            .orElseThrow {
                ResourceNotFoundException(
                    "Employee not exist with id :$id"
                )
            }
        return ResponseEntity.ok(employee)
    }

    // update employee rest api
    @PutMapping("/pokerplanning/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody employeeDetails: Employee): ResponseEntity<Employee> {
        val employee = employeeRepository!!.findById(id).orElseThrow {
            ResourceNotFoundException(
                "Employee not exist with id :$id"
            )
        }

        employee?.firstName = employeeDetails.firstName
        employee?.lastName = employeeDetails.lastName
        employee?.emailId = employeeDetails.emailId
        if (employee != null) {
            val updatedEmployee = employeeRepository.save(employee)
            return ResponseEntity.ok(updatedEmployee)
        }
        return ResponseEntity.ok(null)

    }

    @GetMapping("/pokerplanning/vote")
    fun updateUserVote(@RequestParam ticketNumber: String, @RequestParam vote: String): ResponseEntity<Boolean> {

      val peint=  SecurityContextHolder.getContext().authentication.principal as UserDetails
        println(".......................username....."+peint.username+"....."+vote)
        val authentication = SecurityContextHolder.getContext().authentication

        val userDetails = authentication.principal as UserDetails
        val user = userRepository.findByUsername(userDetails.username)?.orElseThrow {
            IllegalArgumentException("Cannot find user ${userDetails.username}")
        }
        println(".......................username....."+user.username+"....."+vote)
        user.votedPoint = vote
        userRepository.saveAndFlush(user)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/pokerplanning/currentuser")
    fun getCurrentUser(): UserDto {
        println("going to execute gitlab api..")

        val authentication = SecurityContextHolder.getContext().authentication

        val userDetails = authentication.principal as UserDetails
        val user = userRepository.findByUsername(userDetails.username)?.orElseThrow {
            IllegalArgumentException("Cannot find user ${userDetails.username}")
        }

        return UserDto(
            id = user?.id,
            userName = user?.username,
            isModerator = user?.isModerator,
            voted = !(user?.votedPoint.isNullOrEmpty()),
            votedPoint = user?.votedPoint
        )
    }
}
