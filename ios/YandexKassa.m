<<<<<<< HEAD
/* #import "YandexKassa.h"
//@import YandexCheckoutPayments
=======
#import "YandexKassa.h"
>>>>>>> 5e55e47eb1dbe2800591074a58f796af92baf284


@implementation YandexKassa

RCT_EXPORT_MODULE()

<<<<<<< HEAD
RCT_REMAP_METHOD(payment,
                 productName:(NSString *)productName
                 productDescription:(NSString *)productDescription
                 amount:(NSString *)amount
                 findEventsWithResolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    // TODO: Implement some actually useful functionality
	resolve([NSString stringWithFormat: @"productName: %@ productDescription: %@", productName, productDescription]);
}

@end */


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
=======
RCT_EXPORT_METHOD(sampleMethod:(NSString *)stringArgument numberParameter:(nonnull NSNumber *)numberArgument callback:(RCTResponseSenderBlock)callback)
{
    // TODO: Implement some actually useful functionality
	callback(@[[NSString stringWithFormat: @"numberArgument: %@ stringArgument: %@", numberArgument, stringArgument]]);
}

>>>>>>> 5e55e47eb1dbe2800591074a58f796af92baf284
@end
