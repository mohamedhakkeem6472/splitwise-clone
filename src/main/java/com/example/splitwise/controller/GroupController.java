package com.example.splitwise.controller;

import com.example.splitwise.model.Group;
import com.example.splitwise.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        Set<Long> userIds = Set.copyOf((Iterable<Long>) payload.get("userIds"));
        return ResponseEntity.ok(groupService.createGroup(name, userIds));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroup(@PathVariable Long groupId) {
        return groupService.getGroup(groupId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
