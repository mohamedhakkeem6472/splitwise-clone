package com.example.splitwise.service;

import com.example.splitwise.model.Group;
import com.example.splitwise.model.User;
import com.example.splitwise.repository.GroupRepository;
import com.example.splitwise.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public Group createGroup(String name, Set<Long> userIds) {
        Set<User> members = Set.copyOf(userRepository.findAllById(userIds));
        Group group = Group.builder()
                .name(name)
                .members(members)
                .build();
        return groupRepository.save(group);
    }

    public Optional<Group> getGroup(Long groupId) {
        return groupRepository.findById(groupId);
    }
}
