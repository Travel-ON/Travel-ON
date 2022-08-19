# README.md

## Index

> 1. [Team Rules](#team-rules)
> 
> 2. [Convention](#convention)
> 
> 3. [기술 스택](#기술-스택)

# Team Rules

> 공통3반 1팀의 규칙입니다.

⚠️ **이슈 발생시**

- 다음과 같은 내용을 정리하여 노션에 기록한다
  
  - 문제 상황
  
  - 문제 해결하려고 시도한 내용

🕙 **오후 10시 Rule**

- 이 이후에 발생하는 이슈, 오류는 노션에 작성한다

⛔ **오류 발생시**

- 해결 여부 상관X 무조건 노션 기록 [Trouble Shooting](https://little-condor-54f.notion.site/Trouble-Shooting-be0e334cf078454aa332334697456c5d)

⌛ **회의 시간**

- 스크럼 미팅은 15분

- 회의시간은 1시간 ( 단, 55분 경과시 서서 진행)

🗓️ **무슨 일이 있을 경우**

- 최소 하루, 되도록 빠르게 팀원들에게 공유할 것!

⛱️ **기본적으로 주말 Touch X**

- 바쁜 경우 파트별 동의하에 OK

---

# Convention

#### ⭐️ 컨벤션 규칙 지켜주세요!

- [Git 컨벤션](https://little-condor-54f.notion.site/COMMIT-Convention-acfa64e3d6c840a1b4766273442e0adf)
  
  > NAME_tag: subject 형식입니다.
  > 예시: Jaehyun_Docs: README.md 수정

- [Jira 컨벤션](https://little-condor-54f.notion.site/Jira-549149e42e4c4fe3836455985b842ff9)

- [Back-end Code 컨벤션](https://little-condor-54f.notion.site/d5266c8265ba4350abe483ccaacc5b10)

- [JavaScript Code 컨벤션](https://little-condor-54f.notion.site/JS-4b8338a8790243da8db29f496873e999)

- [HTML/CSS Code 컨벤션](https://little-condor-54f.notion.site/HTML-CSS-456a804c8be644eb9492be737cf55267)

  

---

# 프로젝트 소개



**Travel-ON**은 프라이빗한 시공간에서 새로운 사람을 만나는 여행지 위치인증기반 화상채팅 매칭 서비스입니다.

1인 가구가 빠르게 증가하면서, 다양한 시장에서의 주 소비층 또한 1인가구로 빠르게 전환되고 있습니다.

그 중 혼자 여행을 가는 ‘혼행’ 또한 1인가구가 대세가 되어가는 가운데, 1인 여행객에 대한 세분화된 서비스는 전략이 부족하다고 합니다. 그래서 여행객들이 온라인으로 소통하고, 추억을 공유할 수 있는 화상채팅 플랫폼, Travel-ON을 개발하였습니다.



---



# 프로젝트 정보



## :page_facing_up:기술 스택



> - 형상관리: Gitlab
> 
> - 이슈관리: Jira
> 
> - 커뮤니케이션: Mattermost
> 
> - 디자인: Figma
> 
> - OS: Windows 10
> 
> - DB: MySQL 8.0.30
>   
>   ## Front-End
> 
> - HTML5, CSS3, JavaScript (ES6)
> 
> - Vue.js 3.0.11
> 
> - Vuetify
> 
> - Node.js 16.16.0
>   
>   ## Back-End
> 
> - Java JDK zulu-8.jdk
> 
> - Lombok 1.18.24
> 
> - JPA
>   
>   ## IDE
> 
> - MySQL Workbench
> 
> - intellij
> 
> - Visual Studio Code 1.69.0

---

# 프로젝트 결과물



## :page_facing_up: 주요 기능

1. 위치인증 및 칭호

​		브라우저 위치 엑세스를 허용하면 로그인 후 사용자의 위치를 받아온다.

​		위치 인증이 완료되면 상단 네비바 우측에 사용자의 위치가 시 / 구 / 동 으로 표시된다.

​		최초 로그인시 위치 인증을 하면 해당 지역의 업적이 증가하고 칭호 획득 조건을 만족하면, 칭호를 얻을 수 있다.

​		칭호는 네비바 우측에 사용자 닉네임을 누르면 나오는 마이페이지에서 설정할 수 있다.

1. 여행플래너 및 여행스토리

- 상단 네비바 좌측에 있는 여행플래너를 통해 여행 플랜을 작성할 수 있다.
- 여행 플랜은 [방문한 장소 / 방문예정장소] 두가지를 작성할 수 있다.
- 방문한 장소 플랜은 방문한 날짜, 지역, 장소를 필수로 입력하고, 리뷰를 선택적으로 입력한후 저장할 수 있다.
- 작성한 플랜은 좌측 리스트를 클릭하여 상세확인할 수 있다.
- 방문 예정 장소는 방문 예정 날짜, 지역, 장소를 입력할 수 있다.
- 방문 예정 장소는 디데이가 표시되며, 방문을 했을 경우 하단 버튼을 통해 방문한 장소로 플랜을 변경할 수 있다.
- 여행 플래너는 화상채팅방에서 화상화면의 3dot 메뉴를 통해 상대방의 여행스토리로 확인할 수 있다.
- 여행 스토리는 상대방이 작성한 방문한 장소와 방문 예정 장소를 확인할 수 있다.

1. 방 만들기 / 방 매칭하기 / 방 코드로 입장
   1. 방 만들기

- 상단 네비바를 통해 방 만들기 페이지로 이동할 수 있다.

- 방만들기 에서는 참여 가능한 최대 인원을 [4, 6, 8]명으로 제한할 수 있다.

- 위치인증을 기반으로 현재 지역범위를 시, 군구, 동으로 나누어 방을 만들 수 있으며, 방이 생성하게 되었을 때 참여자들 또한 같은 지역범위를 설정해야 참여가 가능하다.

- 위치인증을 기반으로 현재 위치가 마이페이지에 입력한 거주지역과 같은 지역이라면 추가로 현지인 마크를 표시할 수 있게 된다.

  b. 방 매칭하기

- 비공개 설정을 통해 방코드를 공유받은 사람만 접근할 수 있도록 방을 생성할 수 있다.

- 미리보기 화면에서는 화상채팅방에 들어갔을 때, 나의 비디오에 보여질 화면을 미리 볼 수 있으며, 표시되는 것으로는 현지인, 칭호, 닉네임이 있다.

  c. 방 코드 입장

- 방코드입장은 기존에 존재하는 방의 코드를 입력하여 그 방에 참여할 수 있도록하는 기능이다.

- 방코드 입력을 통해 들어갈 수 있는 방의 경우, 지역범위는 제한이 없다.

- 미리보기 화면에서는 화상채팅방에 들어갔을 때, 나의 비디오에 보여질 화면을 미리 볼 수 있으며, 표시되는 것으로는 현지인, 칭호, 닉네임이 있다.

1. 게임

​		화상채팅방에 3인 이상이 있을 경우 게임을 시작할 수 있다.

​		화면 하단에 있는 [게임하기] 버튼을 누르면 [ 룰렛 / 라이어 ] 게임 두 가지 중 하나를 선택할 수 있다.

1. 룰렛

- 화상 화면 겉에 하늘색 프레임이 노란색으로 바뀌면서 무작위로 한명이 선택되고, 결과화면이 보여진다.

b. 라이어

- 방장이 10가지 주제 중 하나를 선택하면 개별적으로 주제에 맞는 키워드 하나 혹은 라이어 메세지가 보여진다.
- 방장이 하단에 있는 [대화시작] 버튼을 눌러 키워드에 대해 설명하는 시간을 갖는다.
- 이야기가 끝나면, 방장은 하단에 있는 [대화종료] 버튼을 누른다.
- 각자 화면에 라이어 투표창이 뜨고, 사람들은 자신이 생각하는 라이어를 선택한다. (라이어를 최다 득표한 사람이 2인 이상일 경우 재투표를 실시한다.)
- 라이어를 찾지 못하면, 라이어의 승리 화면이 보여진다.
- 라이어를 찾으면, 라이어는 자신이 생각한 키워드를 입력한다. 키워드를 맞추면 라이어의 승리 화면이 보여진다.

1. 해시태그

​		화상채팅창 우측에 있는 채팅창에서 해시태그 기능을 사용할 수 있다.

​		채팅창 하단에 [#] 버튼을 누르고 이미지를 확인하고 싶은 장소를 입력한다.

​		채팅창에 대표이미지 1장과 함께 [더보기] 버튼이 보이고, 더보기를 누르면 네이버 이미지 검색화면이 새창으로 뜨게된다.

## :page_facing_up: 결과 예시 화면



### :desktop_computer: 회원가입 페이지

![회원가입](https://user-images.githubusercontent.com/97648026/185531905-9e02ef10-bdc0-4bba-be84-90156a0e90aa.png)



## :desktop_computer: 로그인 화면



![로그인](https://user-images.githubusercontent.com/97648026/185532102-dc58a194-8af6-479a-86b7-dd54f70371ba.png)



## :desktop_computer: 칭호 설정 화면



![칭호설정](https://user-images.githubusercontent.com/97648026/185532141-c84d436f-6cbd-4bd0-b419-8c6f9510a555.png)



## :desktop_computer: 플래너 작성/열람 화면



![플래너](https://user-images.githubusercontent.com/97648026/185532213-340a85e8-a3e2-454d-928f-b14cd81c3b95.png)



## :desktop_computer: 화상 채팅 방 생성 화면



![방 생성](https://user-images.githubusercontent.com/97648026/185532278-03b27423-0315-4851-9dd8-62f91d8501ad.png)



## :desktop_computer: 화상 채팅 방 매칭 화면



![방 매칭](https://user-images.githubusercontent.com/97648026/185532325-e03b9815-5dd3-4b98-9efd-3fdc986a014b.png)



## :desktop_computer: 화상 채팅 방 코드 입장 화면



![방 코드 입장](https://user-images.githubusercontent.com/97648026/185532351-8455efa7-4deb-4646-8ef4-2355461995ac.png)



## :desktop_computer: 화상 채팅 방 화면



![화상 채팅 방](https://user-images.githubusercontent.com/97648026/185532540-198b930b-0fc1-4a53-bdb9-f8dcf77a5f14.png)



## :desktop_computer: 화상 채팅 창

![채팅 창](https://user-images.githubusercontent.com/97648026/185532621-4147aabe-049d-487e-b675-e3ef86a82c62.png)



## :desktop_computer: 플래너 열람 화면

![플래너 열람](https://user-images.githubusercontent.com/97648026/185532706-6f41254e-a48a-452d-a814-6b6fc17f2daf.png)



## :desktop_computer: 게임 화면

![안내](https://user-images.githubusercontent.com/97648026/185532780-1bad07af-ecbc-401c-9e29-3f142652c5db.png)
