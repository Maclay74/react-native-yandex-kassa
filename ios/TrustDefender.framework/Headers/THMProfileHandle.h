/*!
//  Profile.h
//  TrustDefender
//
//  Created by Samin Pour
//  Copyright © 2017 ThreatMetrix. All rights reserved.
//
*/

#ifndef TrustDefender_THMProfileHandle_h
#define TrustDefender_THMProfileHandle_h

#if defined(__has_feature) && __has_feature(modules)
@import Foundation;
#else
#import <Foundation/Foundation.h>
#endif

#define THM_NAME_PASTE2( a, b) a##b
#define THM_NAME_PASTE( a, b) THM_NAME_PASTE2( a, b)

#ifndef THM_PREFIX_NAME
#define NO_COMPAT_CLASS_NAME
#define THM_PREFIX_NAME
#endif

#define THMProfileHandle                THM_NAME_PASTE(THM_PREFIX_NAME, THMProfileHandle)


@interface THMProfileHandle : NSObject

/*! Session ID used for profiling. */
@property(nonatomic, readonly) NSString *sessionID;

-(instancetype) init NS_UNAVAILABLE;
+(instancetype) allocWithZone:(struct _NSZone *)zone NS_UNAVAILABLE;
+(instancetype) new NS_UNAVAILABLE;

/*!
 * Cancels profiling if running, if profiling is finished just returns.
 */
-(void) cancel;

@end

#endif
