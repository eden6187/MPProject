# MPProject
< 참고 사항 >
1. 변수 이름 짓기:
https://jeroenmols.com/blog/2016/03/07/resourcenaming/
위의 사이트를 참조해서 작성해주시면 감사하겠습니다. 변수명 자동완성되니까 길어도 되니 가독성이 있게 작성해주시면 감사하겠습니다

2. 각 액티비티에서 View 관련 설정은 서로 알아보기 쉽도록 initView()메소드로 묶어서 작성해주세요

3. Listener나 Adapter등 중복적으로 사용될 수 있는 것들은 가능하면 따로 package를 만들어서 빼주세요 NavigationView의 아이템들이 선택되었을 때
작동하는 Listener를 lisetners package에 따로 빼두었으니 확인하시기 바랍니다.

4. 액티비티를 새로 만들고자 할 때는 레이아웃은 MainActivitiy의 레이아웃을 가져다 쓰시고 안에 내용만 바꾸면 됩니다. <- 이해안가시면 카톡 주세요

5. 
