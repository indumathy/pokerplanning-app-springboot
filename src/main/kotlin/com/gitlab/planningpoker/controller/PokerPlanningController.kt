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


        val Valueis1 = listOf<IssuesInEstimationDto>(
            IssuesInEstimationDto().apply {
                this.id = 1
                this.iid = 11111
                this.title = "sample title1 - this is the sample tile for ticket 1"
                this.labels = listOf("IN_PROGRESS", " IMP")
            },
            IssuesInEstimationDto().apply {
                this.id = 2
                this.iid = 22222
                this.title = "sample title2 -->> his is the sample tile for ticket 2"
                this.labels = listOf("IMP", " TECH")
            },
            IssuesInEstimationDto().apply {
                this.id = 3
                this.iid = 33333
                this.title = "sample title--3 -->> his is the sample tile for ticket 3"
                this.labels = listOf("BUG", " LOW")
            },
            IssuesInEstimationDto().apply {
                this.id = 4
                this.iid = 44444
                this.title = " this is the sample tile for ticket 444"
                this.labels = listOf("IN_PROGRESS", " IMP")
            },
            IssuesInEstimationDto().apply {
                this.id = 5
                this.iid = 25555
                this.title = "  this is the sample tile for ticket 555"
                this.labels = listOf("IMP", " TECH")
            },
            IssuesInEstimationDto().apply {
                this.id = 6
                this.iid = 66633
                this.title = "sample title--3 -->> his is the sample tile for ticket 666"
                this.labels = listOf("BUG", " LOW")
            }

        ).toMutableList()
        return Valueis1
    }

    @PutMapping("/pokerplanning/gitlab/assignAsModerator/{username}")
    fun assignAsModerator(@PathVariable username: String): ResponseEntity<Boolean> {
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

        val peint = SecurityContextHolder.getContext().authentication.principal as UserDetails
        println(".......................username....." + peint.username + "....." + vote)
        val authentication = SecurityContextHolder.getContext().authentication

        val userDetails = authentication.principal as UserDetails
        val user = userRepository.findByUsername(userDetails.username)?.orElseThrow {
            IllegalArgumentException("Cannot find user ${userDetails.username}")
        }
        println(".......................username....." + user.username + "....." + vote)
        user.votedPoint = vote
        userRepository.saveAndFlush(user)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/pokerplanning/resetallvotes")
    fun resetAllVotes(): ResponseEntity<Boolean> {

        val users = userRepository.findAll()
        users.forEach {
            it.votedPoint = null
            it.showVote = false
        }
        userRepository.saveAll(users)
        return ResponseEntity.ok(true)
    }

    @GetMapping("/pokerplanning/showallvotes")
    fun showAllVotes(): ResponseEntity<Boolean> {

        val users = userRepository.findAll()
        users.forEach {  it.showVote = true }
        userRepository.saveAll(users)
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
