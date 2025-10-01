package com.example.splitwise.service;

import com.example.splitwise.dto.GroupRequest;
import com.example.splitwise.model.Group;
import com.example.splitwise.model.User;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for managing groups.
 */
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new group with given members.
     * Uses SERIALIZABLE isolation to prevent concurrent modifications.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Group createGroup(GroupRequest request) {
        Set<User> members = new HashSet<>(userRepository.findAllById(request.getMemberIds()));

        if (members.isEmpty()) {
            throw new IllegalArgumentException("Group must have at least one member");
        }

        Group group = Group.builder()
                .name(request.getName())
                .members(members)
                .build();

        return groupRepository.save(group);
    }

    /**
     * Fetch a group by ID in READ_ONLY mode.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public Group getGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));
    }
}
