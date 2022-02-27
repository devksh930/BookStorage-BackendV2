

INSERT INTO user (id, created_date, modified_date, email, email_verified, nickname, password, profile_image_url, role_type, user_id) VALUES (0x1CE5ADEF8DC54BB48C19BF4DEA9C5348, '2022-02-27 15:12:41.465844', '2022-02-27 15:12:41.465844', 'test@test.com', false, '닉네임3', '$2a$10$Daxksrs3sW8F93UfV7x6p.3sVKAnERqpU2VWkXgTIZkKfUEOlW0h2', 'DEFAULT_IMAGE', 'ROLE_NOT_CERTIFIED', 'test');
INSERT INTO user (id, created_date, modified_date, email, email_verified, nickname, password, profile_image_url, role_type, user_id) VALUES (0x9730EAA3A9564D9B94D94D3216443D78, '2022-02-27 15:20:44.489067', '2022-02-27 15:20:44.489067', 'test2@test.com', false, '닉네임2', '$2a$10$mGewBH3lzXALmBELgBWss.eery.x1TjpUDaeNAxpk5A5zvaD7vGAm', 'DEFAULT_IMAGE', 'ROLE_NOT_CERTIFIED', 'test2');

INSERT INTO book (id, created_date, modified_date, author, description, discount, image, isbn, link, price, pubdate, publisher, title) VALUES (1, '2022-02-27 15:12:46.681998', '2022-02-27 15:12:46.681998', '마르친 모스칼라', '실제 개발 사례를 통해 알려주는 코드 품질 향상 전략

이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서입니다. 코틀린에 어떤 기능이 있는지, 어떤 표준 라이브러리가 있는지 알고 있다고 코틀린을 강력하고 실용적으로 사용할 수 있는 것은 아닙니다. 코틀린을 제대로 사용하려면, 그 기능을... ', '25200', 'https://bookthumb-phinf.pstatic.net/cover/214/240/21424027.jpg?type=m1&udate=20220116', '8966263372 9788966263370', 'http://book.naver.com/bookdb/book_detail.php?bid=21424027', '28000', '20220121', '인사이트', '이펙티브 코틀린');
INSERT INTO book (id, created_date, modified_date, author, description, discount, image, isbn, link, price, pubdate, publisher, title) VALUES (2, '2022-02-27 15:16:56.831148', '2022-02-27 15:16:56.831148', '데이비드 토머스|앤드류 헌트', '실용주의 프로그래머 20주년 기념판
전문가를 향한 여정

《실용주의 프로그래머》는 당신이 읽고, 또 읽고, 수년간 또다시 읽게 될 몇 안 되는 기술 서적이다. 당신이 이 분야에 처음 발을 디딘 사람이건, 경험 많은 전문가이건 매번 새로운 통찰을 얻게 될 것이다.

데이비드 토머스와 앤드류 헌트는 소프트웨어... ', '29700', 'https://bookthumb-phinf.pstatic.net/cover/215/100/21510061.jpg?type=m1&udate=20220212', '8966263364 9788966263363', 'http://book.naver.com/bookdb/book_detail.php?bid=21510061', '33000', '20220224', '인사이트', '실용주의 프로그래머 (20주년 기념판)');
INSERT INTO book (id, created_date, modified_date, author, description, discount, image, isbn, link, price, pubdate, publisher, title) VALUES (3, '2022-02-27 15:17:21.489713', '2022-02-27 15:17:21.489713', '야마자키 야스시|미나와 케이코|아제카츠 요헤이|사토 타카히코', '다양한 환경에서 저자들이 직접 체득한 인프라 기술의 핵심을 포함해 아키텍처나 네트워크, 서버, 프로세스 등과 같은, 이른바 기반 기술을 어떻게 하면 독자들이 쉽게 이해할 수 있을지에 대한 저자들의 고민이 잘 녹아 있다.

시스템 각 부분의 공통된 원리(구조)를 올바로 이해할 수 있으며, 그럼으로써 새로운... ', '23400', 'https://bookthumb-phinf.pstatic.net/cover/175/271/17527140.jpg?type=m1&udate=20210724', '1190665204 9791190665209', 'http://book.naver.com/bookdb/book_detail.php?bid=17527140', '26000', '20201209', '제이펍', '그림으로 공부하는 IT 인프라 구조');
INSERT INTO book (id, created_date, modified_date, author, description, discount, image, isbn, link, price, pubdate, publisher, title) VALUES (4, '2022-02-27 15:17:34.687044', '2022-02-27 15:17:34.687044', '고재성|이상훈', '클라우드/데브옵스 시대에 알아야 할 인프라 지식
서버실이 있고, 서버 관리자가 따로 있었던 시대를 지나 클라우드 서비스가 보편화되었다. 클라우드 서비스로 넘어오면서 개발자가 직접 서버의 배포, 테스트를 하게 되었고, 네트워크 배치 등을 직접 하게 되면서 인프라 지식이 필수가 되었다. 클라우드... ', '32400', 'https://bookthumb-phinf.pstatic.net/cover/168/749/16874927.jpg?type=m1&udate=20211207', '1165213184 9791165213183', 'http://book.naver.com/bookdb/book_detail.php?bid=16874927', '36000', '20201030', '길벗', 'IT 엔지니어를 위한 네트워크 입문 (클라우드 데브옵스 시대의 필수 역량!)');

INSERT INTO book_storage (id, created_date, modified_date, book_read_type, book_id, user_id) VALUES (1, '2022-02-27 15:12:46.700208', '2022-02-27 15:12:46.700208', 'READ', 1, 0x1CE5ADEF8DC54BB48C19BF4DEA9C5348);
INSERT INTO book_storage (id, created_date, modified_date, book_read_type, book_id, user_id) VALUES (2, '2022-02-27 15:16:56.844252', '2022-02-27 15:16:56.844252', 'READ', 2, 0x1CE5ADEF8DC54BB48C19BF4DEA9C5348);
INSERT INTO book_storage (id, created_date, modified_date, book_read_type, book_id, user_id) VALUES (3, '2022-02-27 15:17:21.498436', '2022-02-27 15:17:21.498436', 'READ', 3, 0x1CE5ADEF8DC54BB48C19BF4DEA9C5348);
INSERT INTO book_storage (id, created_date, modified_date, book_read_type, book_id, user_id) VALUES (4, '2022-02-27 15:17:34.695453', '2022-02-27 15:17:34.695453', 'READ', 4, 0x1CE5ADEF8DC54BB48C19BF4DEA9C5348);
INSERT INTO book_storage (id, created_date, modified_date, book_read_type, book_id, user_id) VALUES (5, '2022-02-27 15:20:53.499885', '2022-02-27 15:20:53.499885', 'READ', 4, 0x9730EAA3A9564D9B94D94D3216443D78);


INSERT INTO book_post (id, created_date, modified_date, book_post_type, content, count, description, is_deleted, is_post_private, like_count, title, bookstorage_id) VALUES (1, '2022-02-27 15:12:52.225306', '2022-02-27 15:12:52.225306', 'FEED', '## 실제 개발 사례를 통해 알려주는 코드 품질 향상 전략


- 이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서다.
- 코틀린에 어떤 기능이 있는지, 어떤 표준 라이브러리가 있는지 알고 있다고 코틀린을 강력하고 실용적으로 사용할 수 있는 것은 아니다.
-  코틀린을 제대로 사용하려면, 그 기능을 언제, 어떻게 적절하게 사용해야 하는지 알아야 한다.


이 책은 많은 사람이 제대로 활용하지 못하고 있는 기능을 간단한 규칙으로 제시하고, 52가지 아이템을 실제 사례를 통해 자세하게 설명한다. 각각의 아이템은 코틀린의 기본적인 기능부터 인라인 함수, 클래스, DSL, 플랫폼 타입과 같은 고급 주제까지 다루고 있다. 이 책을 통해 코틀린의 코드 품질(안전성, 가독성, 코드 설계, 효율성)을 어떻게 향상시킬 수 있는지 배울 수 있을 것이다. 또한 어떻게 하면 안전성, 가독성, 유지보수성, 성능 면에서 더 나은 코틀린 코드를 작성할 수 있는지 알게 될 것이다.
', 0, ' 실제 개발 사례를 통해 알려주는 코드 품질 향상 전략 이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서다  코틀린에 어떤 기능이 있는지 어떤 ', false, false, 0, '이펙티브 코틀린 리뷰', 1);
INSERT INTO book_post (id, created_date, modified_date, book_post_type, content, count, description, is_deleted, is_post_private, like_count, title, bookstorage_id) VALUES (2, '2022-02-27 15:12:59.516306', '2022-02-27 15:12:59.516306', 'FEED', '## 실제 개발 사례를 통해 알려주는 코드 품질 향상 전략


- 이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서다.
- 코틀린에 어떤 기능이 있는지, 어떤 표준 라이브러리가 있는지 알고 있다고 코틀린을 강력하고 실용적으로 사용할 수 있는 것은 아니다.
-  코틀린을 제대로 사용하려면, 그 기능을 언제, 어떻게 적절하게 사용해야 하는지 알아야 한다.


이 책은 많은 사람이 제대로 활용하지 못하고 있는 기능을 간단한 규칙으로 제시하고, 52가지 아이템을 실제 사례를 통해 자세하게 설명한다. 각각의 아이템은 코틀린의 기본적인 기능부터 인라인 함수, 클래스, DSL, 플랫폼 타입과 같은 고급 주제까지 다루고 있다. 이 책을 통해 코틀린의 코드 품질(안전성, 가독성, 코드 설계, 효율성)을 어떻게 향상시킬 수 있는지 배울 수 있을 것이다. 또한 어떻게 하면 안전성, 가독성, 유지보수성, 성능 면에서 더 나은 코틀린 코드를 작성할 수 있는지 알게 될 것이다.
', 0, ' 실제 개발 사례를 통해 알려주는 코드 품질 향상 전략 이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서다  코틀린에 어떤 기능이 있는지 어떤 ', false, false, 0, '이펙티브 코틀린 리뷰', 1);
INSERT INTO book_post (id, created_date, modified_date, book_post_type, content, count, description, is_deleted, is_post_private, like_count, title, bookstorage_id) VALUES (3, '2022-02-27 15:13:00.664272', '2022-02-27 15:13:00.664272', 'FEED', '## 실제 개발 사례를 통해 알려주는 코드 품질 향상 전략


- 이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서다.
- 코틀린에 어떤 기능이 있는지, 어떤 표준 라이브러리가 있는지 알고 있다고 코틀린을 강력하고 실용적으로 사용할 수 있는 것은 아니다.
-  코틀린을 제대로 사용하려면, 그 기능을 언제, 어떻게 적절하게 사용해야 하는지 알아야 한다.


이 책은 많은 사람이 제대로 활용하지 못하고 있는 기능을 간단한 규칙으로 제시하고, 52가지 아이템을 실제 사례를 통해 자세하게 설명한다. 각각의 아이템은 코틀린의 기본적인 기능부터 인라인 함수, 클래스, DSL, 플랫폼 타입과 같은 고급 주제까지 다루고 있다. 이 책을 통해 코틀린의 코드 품질(안전성, 가독성, 코드 설계, 효율성)을 어떻게 향상시킬 수 있는지 배울 수 있을 것이다. 또한 어떻게 하면 안전성, 가독성, 유지보수성, 성능 면에서 더 나은 코틀린 코드를 작성할 수 있는지 알게 될 것이다.
', 0, ' 실제 개발 사례를 통해 알려주는 코드 품질 향상 전략 이 책은 더 나은 코틀린 개발자가 될 수 있도록 도움을 주는 안내서다  코틀린에 어떤 기능이 있는지 어떤 ', false, false, 0, '이펙티브 코틀린 리뷰', 1);
INSERT INTO book_post (id, created_date, modified_date, book_post_type, content, count, description, is_deleted, is_post_private, like_count, title, bookstorage_id) VALUES (4, '2022-02-27 15:20:15.914002', '2022-02-27 15:20:15.914002', 'FEED', '# 실용주의 프로그래머

- 『실용주의 프로그래머』는 당신이 읽고, 또 읽고, 수년간 또다시 읽게 될 몇 안 되는 기술 서적이다.
- 당신이 이 분야에 처음 발을 디딘 사람이건, 경험 많은 전문가이건 매번 새로운 통찰을 얻게 될 것이다.
- 데이비드 토마스와 앤드류 헌트는 소프트웨어 산업에 큰 영향을 미친 이 책의 1판을 1999년에 썼다.
- 고객들이 더 나은 소프트웨어를 만들고 코딩의 기쁨을 재발견하도록 돕기 위해서였다. 이 책의 가르침 덕분에 한 세대에 걸친 프로그래머들이 어떤 언어나 프레임워크, 방법론을 사용하든 상관없이 소프트웨어 개발의 본질을 돌아볼 수 있었다.

-  그리고 실용주의 철학은 수백 권의 책, 스크린캐스트, 오디오북으로 그리고 무수한 사람들의 경력과 성공 스토리로 퍼져 나갔다.', 0, ' 실용주의 프로그래머 실용주의 프로그래머는 당신이 읽고 또 읽고 수년간 또다시 읽게 될 몇 안 되는 기술 서적이다  당신이 이 분야에 처음 발을 디딘 사람이건', false, false, 0, '실용적인 프로그래머 피드', 2);

