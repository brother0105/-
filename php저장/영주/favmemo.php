<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $id=(int)$_POST['id'];
        $memo=$_POST['memo'];

        if(empty($id)){
            $errMSG = "레시피 번호가 들어오지 않았습니다.";
        }

        if(!isset($errMSG)) // 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 ingretable 테이블에 저장합니다. 
                $stmt = $con->prepare('UPDATE recipe_favorite SET f_memo=:memo WHERE recipe_num=:id');
                $stmt->bindParam(':id', $id);
                $stmt->bindParam(':memo', $memo);

                if($stmt->execute())
                {
                    $successMSG = "메모를 수정했습니다.";
                }
                else
                {
                    $errMSG = "메모 수정 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>