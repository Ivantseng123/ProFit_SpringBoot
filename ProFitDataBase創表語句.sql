CREATE TABLE users(
    user_id INT IDENTITY(100, 1) NOT NULL PRIMARY KEY,
    user_name NVARCHAR(30),
    user_email VARCHAR(50) UNIQUE,
    user_passwordHash VARCHAR(128), 
    user_phoneNumber VARCHAR(20),
    user_city NVARCHAR(60),
    user_identity TINYINT,
    user_pictureURL VARCHAR(MAX),
    user_balance INT,	
    freelancer_location_prefer NVARCHAR(50),
    freelancer_exprience NVARCHAR(30),
    freelancer_identity NVARCHAR(20),
    freelancer_profile_status TINYINT NOT NULL,
    freelancer_description NTEXT,
    user_register_time DATETIME2(0) DEFAULT DATEADD(HOUR, 8, GETDATE()),
    enabled TINYINT,
    verification_code VARCHAR(100)
);
 



CREATE TABLE employer_application(
    employer_application_id INT IDENTITY(100, 1) NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    company_name NVARCHAR(30), 
    company_address NVARCHAR(60),
    company_category NVARCHAR(20),
    company_phoneNumber VARCHAR(20),
    company_taxID VARCHAR(100),
    company_taxID_docURL NVARCHAR(MAX),
    user_national_id VARCHAR(20),
    idCard_pictureURL_1 NVARCHAR(MAX),
    idCard_pictureURL_2 NVARCHAR(MAX),
    application_check TINYINT,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);


CREATE TABLE employer_profile(
    employer_profile_id INT IDENTITY(100, 1) NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    company_name NVARCHAR(30),
    company_address NVARCHAR(60),
    company_category NVARCHAR(20),
    company_phoneNumber VARCHAR(20),
    company_taxID VARCHAR(100),
    company_numberOfemployee NVARCHAR(30),
    company_capital NVARCHAR(30),
    company_description NVARCHAR(200),
    company_photoURL NVARCHAR(MAX),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);



CREATE TABLE password_reset_tokens(
    token_id INT IDENTITY(100, 1) NOT NULL PRIMARY KEY,
    user_id INT NOT NULL,
    user_tokenHash VARCHAR(150) UNIQUE,
    expiration_time DATETIME2(0) DEFAULT DATEADD(MINUTE, 30, CURRENT_TIMESTAMP),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE jobs (
    jobs_id INT PRIMARY KEY IDENTITY(1,1), 
    jobs_user_id INT,
    major_category_id INT,
    jobs_title NVARCHAR(100), 
    jobs_posting_date DATETIME2 default DATEADD(HOUR, 8, GETDATE()),
    jobs_application_deadline DATETIME2,
    jobs_description NVARCHAR(MAX), 
    jobs_status TINYINT, 
    jobs_location NVARCHAR(100),
    jobs_max_salary INT, 
    jobs_min_salary INT, 
    jobs_worktime NVARCHAR(50),
    jobs_number_of_openings INT,
    FOREIGN KEY (jobs_user_id) REFERENCES users(user_id)
);

CREATE TABLE jobs_major (
    jobs_id INT PRIMARY KEY,
    major_id INT PRIMARY KEY,
    PRIMARY KEY (jobs_id, major_id),
    FOREIGN KEY (jobs_id) REFERENCES jobs(jobs_id),
    FOREIGN KEY (major_id) REFERENCES major(major_id)
);

-- CREATE TABLE [dbo].[jobs_application] (
--     [jobs_application_id]         INT             IDENTITY (1, 1) NOT NULL,
--     [jobs_application_posting_id] INT             NOT NULL,
--     [jobs_application_member_id]  INT             NOT NULL,
--     [jobs_application_date]       DATE            CONSTRAINT [DEFAULT_jobs_application_jobs_application_date] DEFAULT (getdate()) NULL,
--     [jobs_application_status]     TINYINT         DEFAULT ((0)) NULL,
--     [jobs_application_contract]   VARBINARY (MAX) NULL,
--     PRIMARY KEY CLUSTERED ([jobs_application_id] ASC),
--     CONSTRAINT [FK_jobs_application_member_id] FOREIGN KEY ([jobs_application_member_id]) REFERENCES [dbo].[users] ([user_id]),
--     CONSTRAINT [FK_jobs_application_posting_id] FOREIGN KEY ([jobs_application_posting_id]) REFERENCES [dbo].[users] ([user_id])
-- );
CREATE TABLE [dbo].[jobs_application] (
    [jobs_application_id]         INT             IDENTITY (1, 1) NOT NULL,
    [jobs_application_posting_id] INT             NOT NULL,
    [jobs_application_member_id]  INT             NOT NULL,
    [jobs_application_date]       DATE            CONSTRAINT [DEFAULT_jobs_application_jobs_application_date] DEFAULT (getdate()) NULL,
    [jobs_application_status]     TINYINT         DEFAULT ((0)) NULL,
    [jobs_application_contract]   VARBINARY (MAX) NULL,
    PRIMARY KEY CLUSTERED ([jobs_application_id] ASC),
    CONSTRAINT [FK_jobs_application_member_id] FOREIGN KEY ([jobs_application_member_id]) REFERENCES [dbo].[users] ([user_id]),
    CONSTRAINT [FK_jobs_application_posting_id] FOREIGN KEY ([jobs_application_posting_id]) REFERENCES [dbo].[users] ([user_id])
    );




CREATE TABLE jobs_application_project (
    jobs_application_project_id INT PRIMARY KEY IDENTITY(1,1),
    jobs_application_id INT,
    jobs_application_status TINYINT,
    jobs_project VARCHAR(50),
    jobs_amount INT,
    FOREIGN KEY (jobs_application_id) REFERENCES jobs_application(jobs_application_id) 
);

    -- 專業技能的分類, 一個技能只能屬於一種分類
	CREATE TABLE major_category (
	major_category_id INT primary key,
	category_name NVARCHAR(24) not null,
	);

	-- 一列major代表一個專業技能
	CREATE TABLE major (
	major_id INT PRIMARY KEY,
	major_name NVARCHAR(24) not null,
	major_category_id INT,
	major_description NVARCHAR(48),
	
	FOREIGN KEY (major_category_id) REFERENCES major_category(major_category_id)
	);
	-- user新增的技能(中介table)
	CREATE TABLE user_major (
	user_id INT NOT NULL,
	major_id INT NOT NULL,

	PRIMARY KEY (user_id, major_id),
	FOREIGN KEY(user_id) REFERENCES users(user_id),
	FOREIGN KEY(major_id) REFERENCES major(major_id) 
	);


	-- 服務報價與概述
	-- 一個人可以在一個專業下新增多筆服務
	CREATE TABLE service (
	service_id INT PRIMARY KEY IDENTITY(1,1),
	user_id INT NOT NULL,
	major_id INT NOT NULL,
	service_title NVARCHAR(50),
	service_content NVARCHAR(1000),
	service_price int NOT NULL,
	service_unit_name NVARCHAR(8),
	service_duration DECIMAL(10,1),
	service_createdate DATETIME2,
	service_updatedate DATETIME2,
	-- 建立服務可以存3張圖片當範例
	service_pictureURL_1 VARCHAR(max),
	service_pictureURL_2 VARCHAR(max),
	service_pictureURL_3 VARCHAR(max),
	-- 服務狀態(審核是否通過、)
	service_status int not null default 0,
	
	FOREIGN KEY (user_id,major_id) REFERENCES user_major(user_id,major_id)
	);
	

	CREATE TABLE collection (
	collection_id INT PRIMARY KEY IDENTITY(1,1),
	user_id INT,
	major_id INT,
	collection_cover_img_id INT, 
	collection_name NVARCHAR(24),
	
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (major_id) REFERENCES major(major_id)
	);
	
	
	CREATE TABLE image(
	image_id INT PRIMARY KEY IDENTITY(1,1),
	collection_id INT,
	image_pictureURL VARCHAR(max),
	
	FOREIGN KEY (collection_id) REFERENCES collection(collection_id)
	);
	
	CREATE TABLE video(
	video_id INT PRIMARY KEY IDENTITY(1,1),
	collection_id INT,
	video_url NVARCHAR(max),
	video_disc  NVARCHAR(128)
	
	FOREIGN KEY (collection_id) REFERENCES collection(collection_id)
	);

--建立 events 表格
CREATE TABLE events (
    event_id NVARCHAR(255) PRIMARY KEY,
    event_name NVARCHAR(255) NOT NULL,
    is_event_active TINYINT NOT NULL,
    event_category TINYINT,
    event_major INT,
    event_publish_date DATETIME2(0),
    event_start_date DATETIME2(0),
    event_end_date DATETIME2(0),
    event_part_start_date DATETIME2(0),
    event_part_end_date DATETIME2(0),
    event_amount INT,
    event_location NVARCHAR(255),
    event_participant_maximum INT,
    event_description NVARCHAR(255),
    event_note NVARCHAR(255),
    FOREIGN KEY (event_major) REFERENCES major(major_id)
);

--建立 event_host 表格
CREATE TABLE event_host (
    event_id NVARCHAR(255), 
    event_host_id INT, 
    PRIMARY KEY (event_id, event_host_id), 
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (event_host_id) REFERENCES users(user_id)
);

--建立 event_order 表格
CREATE TABLE event_order (
    event_order_id NVARCHAR(50) PRIMARY KEY,
    event_order_amount INT,
    is_event_order_active BIT,
    event_id NVARCHAR(255),
    event_participant_id INT,
    event_participant_date DATETIME2(0),
    event_participant_note NVARCHAR(255),
    FOREIGN KEY (event_id) REFERENCES events(event_id),
    FOREIGN KEY (event_participant_id) REFERENCES users(user_id)
);





-- courses table
CREATE TABLE [dbo].[courses] (
    [course_id]              NVARCHAR (50)  NOT NULL,
    [course_name]            NVARCHAR (50)  NOT NULL,
    [course_coverPictureURL] NVARCHAR (MAX) NULL,
    [course_create_user_id]  INT            NOT NULL,
    [course_category]        INT            NOT NULL,
    [course_information]     NVARCHAR (MAX) NULL,
    [course_description]     NVARCHAR (MAX) NULL,
    [course_enrollment_date] DATETIME2 (7)  NOT NULL,
    [course_start_date]      DATETIME2 (7)  NULL,
    [course_end_date]        DATETIME2 (7)  NULL,
    [course_price]           INT            NULL,
    [course_status]          NVARCHAR (20)  NOT NULL,
    PRIMARY KEY CLUSTERED ([course_id] ASC),
    FOREIGN KEY ([course_category]) REFERENCES [dbo].[major_category] ([major_category_id]),
    FOREIGN KEY ([course_create_user_id]) REFERENCES [dbo].[users] ([user_id])
);


CREATE TABLE [dbo].[course_order] (
    [course_order_id]          NVARCHAR (50)  NOT NULL,
    [course_id]                NVARCHAR (50)  NOT NULL,
    [student_id]               INT            NOT NULL,
    [course_order_price]       INT            NULL,
    [course_order_create_date] DATETIME2 (7)  NOT NULL,
    [course_order_remark]      NVARCHAR (MAX) NULL,
    [course_order_status]      NVARCHAR (50)  DEFAULT ('Pending') NOT NULL,
    [course_order_payment_method] NVARCHAR(20) NOT NULL,
    [course_order_taxID] INT NULL,
    PRIMARY KEY CLUSTERED ([course_order_id] ASC),
    FOREIGN KEY ([course_id]) REFERENCES [dbo].[courses] ([course_id]),
    FOREIGN KEY ([student_id]) REFERENCES [dbo].[users] ([user_id])
);

CREATE TABLE [dbo].[course_grade_content] (
    [course_grade_id]      INT           IDENTITY (100, 1) NOT NULL,
    [course_id]            NVARCHAR (50) NOT NULL,
    [student_id]           NVARCHAR (50) NOT NULL,
    [course_grade_score]   INT           NULL,
    [course_grade_comment] NVARCHAR (MAX) NULL,
    CONSTRAINT [PK_course_grade_content] PRIMARY KEY CLUSTERED ([course_grade_id] ASC),
    FOREIGN KEY ([course_id]) REFERENCES [dbo].[courses] ([course_id])
);

CREATE TABLE [dbo].[course_module] (
    [course_module_id]   INT           IDENTITY (1, 1) NOT NULL,
    [course_id]          NVARCHAR (50) NOT NULL,
    [course_module_name] NVARCHAR (50) NOT NULL,
    PRIMARY KEY CLUSTERED ([course_module_id] ASC),
    FOREIGN KEY ([course_id]) REFERENCES [dbo].[courses] ([course_id])
);

CREATE TABLE [dbo].[course_lessons] (
    [course_lesson_id]      INT            IDENTITY (1, 1) NOT NULL,
    [course_module_id]      INT            NOT NULL,
    [course_id]             NVARCHAR (50)  NOT NULL,
    [course_lesson_name]    NVARCHAR (50)  NOT NULL,
    [course_lesson_sort]    NVARCHAR (50)  NULL,
    [lesson_media_url]      NVARCHAR (MAX) NULL,
    [lesson_media_type]     NVARCHAR (20)  NULL,
    [lesson_media_duration] INT            NULL,
    PRIMARY KEY CLUSTERED ([course_lesson_id] ASC),
    FOREIGN KEY ([course_id]) REFERENCES [dbo].[courses] ([course_id]),
    FOREIGN KEY ([course_module_id]) REFERENCES [dbo].[course_module] ([course_module_id])
);

-- transaction table
-- 1. 建立 job_orders 表
CREATE TABLE job_orders (
    job_orders_id NVARCHAR(50) PRIMARY KEY,  -- 職缺訂單申請ID，主鍵（PK）
    job_application_id INT,                  -- 職缺申請ID，外鍵（FK）
    job_order_date DATETIME2 NULL,           -- 申請訂單日期，允許 NULL
    job_order_status VARCHAR(10) CHECK (job_order_status IN ('Processing', 'Completed', 'Canceled')) NOT NULL,  -- 申請訂單狀態
    job_notes TEXT,                          -- 訂單備註
    job_amount INT NOT NULL,               -- 訂單總金額，不允許 NULL
    job_order_payment_method NVARCHAR(20);	--付款方式
    job_order_taxID INT;					--統編
    FOREIGN KEY (job_application_id) REFERENCES jobs_application(jobs_application_id)  -- 外鍵約束
);


-- 2. 建立 user_transactions 表
CREATE TABLE user_transactions (
    transaction_id NVARCHAR(50) PRIMARY KEY,         -- 交易ID
    user_id INT NOT NULL,                            -- 用戶ID
    transaction_role NVARCHAR(10) NOT NULL,          -- 交易角色
    transaction_type NVARCHAR(10) NOT NULL,          -- 交易類型
    order_type NVARCHAR(20)							 -- 查詢訂單種類 
    order_id NVARCHAR(50),                           -- 通用的訂單ID
    total_amount int NOT NULL,            -- 交易金額
    platform_fee int NOT NULL DEFAULT 0,  -- 平台抽成
    target_income int NOT NULL,               -- 實際支付給接收方的金額
    transaction_status NVARCHAR(10) NOT NULL,        -- 交易狀態
    payment_method NVARCHAR(20) NOT NULL,            -- 支付方式
    reference_id VARCHAR(100) NULL,                  -- 第三方支付平台的參考ID
    created_at DATETIME2 NOT NULL,   -- 交易創建時間
    completion_at DATETIME2(7) NULL,                 -- 交易完成時間
    FOREIGN KEY (user_id) REFERENCES users(user_id)  -- 外鍵關聯到用戶表
);

-- 3. 建立 invoices 表
CREATE TABLE invoices (
    invoice_number NVARCHAR(50) PRIMARY KEY,   -- 發票號碼，同時作為主鍵（PK）
    transaction_id NVARCHAR(50),               -- 交易ID，與交易一對一關聯
    invoice_amount int NOT NULL,               -- 發票金額
    issued_date DATETIME2 NOT NULL,            -- 發票開具日期
    invoice_status VARCHAR(10) CHECK (invoice_status IN ('open', 'canceled')) NOT NULL, -- 發票狀態
    FOREIGN KEY (transaction_id) REFERENCES user_transactions(transaction_id), -- 外鍵關聯到交易表
);


-------------------------------------------以下是10/18新增的委託,委託訂單,聊天室資料表
-- 合作資料僅限發起人(這裡是案主)可編輯,送出合作邀約,接案客同意合作後,狀態改為不可編輯,案主可 前往付款(新增訂單頁面)
CREATE TABLE service_application(
	service_application_id INT IDENTITY(100, 1) NOT NULL PRIMARY KEY,
	caseowner_user_id INT NOT NULL,			--這裡是 案主 的 user_id
	freelancer_user_id	INT NOT NULL,		--這裡是 提供服務人(任務客) 的 user_id
	--service_id INT,						-- 先不要用到--能從這裡拿到 服務 的 資訊
	jobs_id INT,							--能從這裡拿到 合作的案件(jobs) 的 資訊

	chat_id INT,							-- 外鍵，代表這個service_application屬於哪個聊天室。

	service_application_title NVARCHAR(50) NOT NULL,

	service_application_subitem NVARCHAR(50) NOT NULL,
	service_application_price INT NOT NULL,
	service_application_amount INT NOT NULL,
	service_application_unit NVARCHAR(50) NOT NULL,
	service_application_subitem_id INT,						--沒用到,未來擴充用,一個合作可以有多個子項目(欄位跟上面一樣,報價用)

	service_application_content NVARCHAR(1000),				--合作內容
	service_application_appendix_url NVARCHAR(max),			--合約附件,存firebase後回傳url

	service_application_status INT DEFAULT 0 NOT NULL,      --狀態: 0草稿、1洽談中(只有發起人能編輯其他欄位,另一人只能更改為完成)、2完成(自動成立訂單service_order)、3婉拒、4關閉(由發起人關閉)

	service_application_mission NVARCHAR(50),				-- 接案客需交付項目
	service_application_done_date DATETIME2,				-- 接案客交付(完成)日期
);



-- 依服務委託成立訂單,東榆會從 service_application表 找出付款人跟收款人
CREATE TABLE service_order(
	service_order_id NVARCHAR(50) PRIMARY KEY,		-- 服務委託訂單ID，主鍵（PK）
    service_application_id INT NOT NULL,			-- 服務委託ID，外鍵（FK）
	service_order_payby INT NOT NULL,	 --這裡是 這筆訂單付款人的 user_id
    service_order_date DATETIME2 NULL,				-- 申請訂單日期，允許 NULL
    service_order_status VARCHAR(10) CHECK (service_order_status IN ('Processing', 'Completed', 'Canceled')) NOT NULL,  -- 申請訂單狀態
    service_order_notes NVARCHAR(255),                          -- 訂單備註
	service_order_payment_method NVARCHAR(20),					-- 支付方式
	service_order_taxID INT,						-- 統一編號(如果需要)
    service_order_amount INT NOT NULL,			 -- 訂單總金額，不允許 NULL
 
);



-----------------------聊天室---------------------------
-- 聊天室一定要有userid1、userid2、jobs_id才能成立
-- 可以根據
CREATE TABLE chat(
	chat_id INT IDENTITY(1,1) PRIMARY KEY,
	jobs_id INT NOT NULL,					-- 外鍵，指向 Jobs 表中的 jobs_id，表示該聊天根據哪個 Job 成立。
	user_id1 INT NOT NULL,					-- 外鍵，指向 User 表中的 user_id，表示參與聊天的其中一個用戶。
	user_id2 INT NOT NULL,					-- 外鍵，指向 User 表中的 user_id，表示參與聊天的另一個用戶。
	create_at DATETIME2 DEFAULT DATEADD(HOUR, 8, GETDATE()),			-- 會話開始時間。
	last_message_at DATETIME2,		-- 最後一條訊息的時間，用於顯示會話最近的活動時間。
	status INT DEFAULT 0 NOT NULL,			-- 狀態, 分為 0使用中、 1結束、 2封存

	-- 限制條件，狀態只能有3種、不能跟自己聊天
	CONSTRAINT CK_ChatStatus CHECK (status IN (0, 1, 2)),
    CONSTRAINT CK_DifferentUsers CHECK (user_id1 <> user_id2)
);

CREATE TABLE message(
	message_id INT IDENTITY(1,1)  PRIMARY KEY,
	chat_id INT,					-- 外鍵，指向 Chat 表中的 chat_id，用於標識訊息所屬的會話。
	sender_id INT,					-- 外鍵，指向 User 表中的 user_id，表示發送訊息的用戶。
	
	-- 在 message 表中添加一個字段來表示消息類型：
	message_type VARCHAR(20) DEFAULT 'text' NOT NULL,

	content NVARCHAR(200),			-- 訊息內容。
	sent_at DATETIME2 DEFAULT DATEADD(HOUR, 8, GETDATE()),		-- 訊息發送時間。
	is_read BIT,						-- 布林值，表示該訊息是否已被接收方讀取。

	CONSTRAINT CK_message_type CHECK (message_type IN ('text', 'service_application')),
);

-- 在 message 表上創建兩個索引：idx_chat_id 和 idx_sender_id，用於提高查詢效率
CREATE INDEX idx_chat_id ON message(chat_id);
CREATE INDEX idx_sender_id ON message(sender_id);


-- 創建觸發器來自動更新 last_message_at
-- 每當有新消息插入到 "message" 表時，它會自動更新相應聊天（chat）的 "last_message_at" 字段為當前時間加 8 小時。
CREATE TRIGGER UpdateLastMessageTime
ON message
AFTER INSERT
AS
BEGIN
    UPDATE chat
    SET last_message_at = DATEADD(HOUR, 8, GETDATE())
    WHERE chat_id IN (SELECT chat_id FROM inserted)
END


