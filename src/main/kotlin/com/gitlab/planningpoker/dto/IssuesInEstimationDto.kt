package com.gitlab.planningpoker.dto

import com.fasterxml.jackson.databind.node.ValueNode
import org.gitlab4j.api.Constants.IssueState
import org.gitlab4j.api.models.*
import java.util.*

class IssuesInEstimationDto {
    var assignee: Assignee? = null
    var assignees: List<Assignee>? = null
    var author: Author? = null
    var confidential: Boolean? = null
    var createdAt: Date? = null
    var updatedAt: Date? = null
    var closedAt: Date? = null
    var closedBy: User? = null
    var description: String? = null
    var dueDate: Date? = null
    var actualId: ValueNode? = null
    var externalId: String? = null
    var id: Int? = null
    var iid: Int? = null
    var issueLinkId: Int? = null
    var labels: List<String>? = null
    var milestone: Milestone? = null
    var projectId: Int? = null
    var state: IssueState? = null
    var subscribed: Boolean? = null
    var title: String? = null
    var userNotesCount: Int? = null
    var webUrl: String? = null
    var weight: Int? = null
    var discussionLocked: Boolean? = null
    var timeStats: TimeStats? = null
    var upvotes: Int? = null
    var downvotes: Int? = null
    var mergeRequestsCount: Int? = null
    var hasTasks: Boolean? = null
    var taskStatus: String? = null
    var taskCompletionStatus: Issue.TaskCompletionStatus? = null
}