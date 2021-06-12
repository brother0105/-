<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');


    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {

        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받습니다.

        $id=$_POST['id'];
        
        if(empty($id)){
            $errMSG = "id를 입력하세요.";
        }

        if(!isset($errMSG)) // 모두 입력이 되었다면 
        {
            try{
                // SQL문을 실행하여 데이터를 MySQL 서버의 ingretable 테이블에 저장합니다. 
                $stmt = $con->prepare('DELETE FROM ingretable WHERE id=:id');
                $stmt->bindParam(':id', $id);

                if($stmt->execute())
                {
                    $successMSG = "재료를 삭제했습니다.";
                }
                else
                {
                    $errMSG = "재료 삭제 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>