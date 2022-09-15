package com.codulate.zone.service;

import com.codulate.zone.data.CoordinateDto;
import com.codulate.zone.data.ZoneDto;
import com.codulate.zone.ports.spi.PointValidatePort;
import org.springframework.stereotype.Service;

@Service
public class PointValidateAdapter implements PointValidatePort {

    private static final float INF = 10000;

    @Override
    public boolean validatePoint(ZoneDto zoneDto, CoordinateDto vPoint) {

        return isInside(zoneDto, zoneDto.getCoordinates().size(), vPoint);
    }

    private boolean onSegment(CoordinateDto p, CoordinateDto q, CoordinateDto r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) &&
                q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) &&
                q.getY() >= Math.min(p.getY(), r.getY());
    }

    private float orientation(CoordinateDto p, CoordinateDto q, CoordinateDto r) {
        float val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (val == 0) {
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }

    private boolean doIntersect(CoordinateDto polygonItem, CoordinateDto polygonNextItem,
                                CoordinateDto vPoint, CoordinateDto extreme) {

        float o1 = orientation(polygonItem, polygonNextItem, vPoint);
        float o2 = orientation(polygonItem, polygonNextItem, extreme);
        float o3 = orientation(vPoint, extreme, polygonItem);
        float o4 = orientation(vPoint, extreme, polygonNextItem);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(polygonItem, vPoint, polygonNextItem)) {
            return true;
        }

        if (o2 == 0 && onSegment(polygonItem, extreme, polygonNextItem)) {
            return true;
        }

        if (o3 == 0 && onSegment(vPoint, polygonItem, extreme)) {
            return true;
        }

        return o4 == 0 && onSegment(vPoint, polygonNextItem, extreme);
    }

    private boolean isInside(ZoneDto zoneDto, int listCount, CoordinateDto vPoint) {

        CoordinateDto[] coordinateDtos = zoneDto.getCoordinates().toArray(new CoordinateDto[zoneDto.getCoordinates().size()]);
        if (listCount < 3) {
            return false;
        }

        CoordinateDto extreme = new CoordinateDto(INF, vPoint.getY());

        int decrease = 0;

        int count = 0;
        int i = 0;
        do {
            int next = (i + 1) % listCount;

            if (coordinateDtos[i].getY() == vPoint.getY()) decrease++;

            if (doIntersect(coordinateDtos[i], coordinateDtos[next], vPoint, extreme)) {

                if (orientation(coordinateDtos[i], vPoint, coordinateDtos[next]) == 0) {
                    return onSegment(coordinateDtos[i], vPoint, coordinateDtos[next]);
                }

                count++;
            }
            i = next;
        } while (i != 0);

        count -= decrease;

        return (count % 2 == 1);
    }
}
