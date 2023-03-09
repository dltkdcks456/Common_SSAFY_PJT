package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
    private Long userId;

    @Column(length = 50)
    private String name; 

    @Column(length = 50)
    private String nickname;

	@Column(length = 50)
	private String age;

	@Column(length = 50)
	private String gender;

	@Column(length = 50)
	private String si;

	@Column(length = 50)
	private String gun;

	@Column(length = 50)
	private String dong;

	@Column(length = 50)
	private String email;

	@Column
	private int weight;

	@Column
	private int cycle_weight;
	@Column(length = 50)
	private String id;

	@Column(length = 255)
	private String password;

	@Column
	private int permission;

	@Column(length = 255, name = "image_path")
	private String imagePath;

//	@Column(length = 255, name = "image_uuid_path")
//	private String imageUuidPath;

	@Column
	private boolean open;


    @Builder
    public User(Long userId, String name, String nickname, String age, String gender, String si, String gun, String dong,
				String email, int weight, int cycle_weight, String id, String password, int permission, String imagePath, boolean open) {
		this.userId = userId;
        this.name = name;
		this.nickname = nickname;
		this.age = age;
		this.gender = gender;
		this.si = si;
		this.gun = gun;
		this.dong = dong;
		this.email = email;
		this.weight = weight;
		this.cycle_weight = cycle_weight;
		this.id = id;
		this.password = password;
		this.permission = permission;
		this.imagePath = imagePath;
//		this.imageUuidPath = imageUuidPath;
		this.open = open;
    }

}