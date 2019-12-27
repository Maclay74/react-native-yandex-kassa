//
//  YandexKassa.swift
//  YandexKassa
//
//  Created by Mike on 26/12/2019.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import YandexCheckoutPayments
import YandexCheckoutPaymentsApi

@objc(YandexKassa)
class YandexKassa: UIViewController {
    
    private var jsPromiseResolver: RCTPromiseResolveBlock? = nil;
    private var jsPromiseRejecter: RCTPromiseRejectBlock? = nil;
    private var tokenizationView: UIViewController? = nil;
    
    @objc
    func payment(_ name: String,
                 withDescription description: String,
                 withAmount amount: String,
                 withResolver resolve: @escaping RCTPromiseResolveBlock,
                 withRejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        jsPromiseResolver = resolve;
        jsPromiseRejecter = reject;
        
        let clientApplicationKey = "test_NjYxOTExBiaATDzDGq4rZjnIR7BZmMGUdrduolpwvr8"
        
        let newAmount = Amount(value: Decimal(string: amount)!, currency: .rub)
        
        let tokenizationModuleInputData = TokenizationModuleInputData(clientApplicationKey:    clientApplicationKey,
            shopName: name,
            purchaseDescription: description,
            amount: newAmount)
        
        let inputData: TokenizationFlow = .tokenization(tokenizationModuleInputData)

        DispatchQueue.main.async {
          
            self.tokenizationView = TokenizationAssembly.makeModule(inputData: inputData, moduleOutput: self)
            //present(viewController, animated: true, completion: nil)
            
            let root = UIApplication.shared.keyWindow?.rootViewController
            root?.present(self, animated: true, completion: nil)
            
            if (self.tokenizationView != nil) {
                self.present(self.tokenizationView!, animated: true, completion: nil)
            }
                        
        }
    }
    
    
    @objc
    func show3Dsecure(_ url: String,
                      withResolver resolve: @escaping RCTPromiseResolveBlock,
                      withRejecter reject: @escaping RCTPromiseRejectBlock) -> Void {
        
        self.jsPromiseResolver = resolve;
        self.jsPromiseRejecter = reject;
        
        (self.tokenizationView! as! TokenizationModuleInput).start3dsProcess(requestUrl: url)
        
    }
    
    @objc
    func finish() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else { return }
            self.dismiss(animated: true)
        
            let root = UIApplication.shared.keyWindow?.rootViewController
            root?.dismiss(animated: true, completion: nil)
        }
    }
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
      return true
    }
    
}

extension YandexKassa: TokenizationModuleOutput {
    func tokenizationModule(_ module: TokenizationModuleInput,
                          didTokenize token: Tokens,
                          paymentMethodType: PaymentMethodType) {
        
    
        if (jsPromiseResolver != nil) {
            jsPromiseResolver!(token.paymentToken)
        }
    
    }
    
    func didSuccessfullyPassedCardSec(on module: TokenizationModuleInput) {
        
        self.finish()
        
        if (jsPromiseResolver != nil) {
            jsPromiseResolver!(nil)
        }
        
    }
    
    func needsConfirmPayment(requestUrl: String) {
        (self.tokenizationView! as! TokenizationModuleInput).start3dsProcess(requestUrl: requestUrl)
    }

    func didFinish(on module: TokenizationModuleInput,
                   with error: YandexCheckoutPaymentsError?) {
      
        self.finish()
        
        if (jsPromiseRejecter != nil) {
            jsPromiseRejecter!("500", "Something went wrong", nil)
        }
    }
}
