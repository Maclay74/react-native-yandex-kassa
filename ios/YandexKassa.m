#import "React/RCTBridgeModule.h"
#import "React/RCTUtils.h"
@interface RCT_EXTERN_MODULE(YandexKassa, UIViewController)

RCT_EXTERN_METHOD(payment:
                  (NSString)name
                  withDescription: (NSString)description
                  withAmount: (NSString)amount
                  withResolver: (RCTPromiseResolveBlock)resolve
                  withRejecter: (RCTPromiseRejectBlock)reject
)

RCT_EXTERN_METHOD(show3Dsecure:
                  (NSString)url
                  withResolver: (RCTPromiseResolveBlock)resolve
                  withRejecter: (RCTPromiseRejectBlock)reject
)

RCT_EXTERN_METHOD(finish)
@end