import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import groovy.json.JsonSlurper as JsonSlurper

def BaseURL = GlobalVariable.BaseURL

def RequestObject = findTestObject('POSTRegister')

def path = 'api/users'

System.out.println('request body:' + RequestObject.getBodyContent())

RequestObject.setRestUrl(BaseURL + path)

def Response = WS.sendRequest(RequestObject)

WS.verifyResponseStatusCode(Response, 201)

System.out.println('get data : ' + Response.getResponseText())

JsonSlurper slurper = new JsonSlurper()

Map parsedJson = slurper.parseText(Response.getResponseText())

GlobalVariable.UserId = parsedJson.id

def RequestObjectUser = findTestObject('GETSingleUser')

def pathGetUser = 'api/users/'

RequestObjectUser.setRestUrl((BaseURL + pathGetUser) + GlobalVariable.UserId)
System.out.println("test123"+RequestObjectUser.getRestUrl())
def ResponseUser = WS.sendRequest(RequestObjectUser)

WS.verifyResponseStatusCode(ResponseUser, 200)

Map UserData = slurper.parseText(ResponseUser.getResponseText())

WS.verifyEqual(UserData.name, 'Alvin Darel')

